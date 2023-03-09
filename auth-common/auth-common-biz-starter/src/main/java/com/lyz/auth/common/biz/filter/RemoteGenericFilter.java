package com.lyz.auth.common.biz.filter;

import com.lyz.auth.common.remote.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.beanutil.JavaBeanAccessor;
import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.config.Configuration;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.io.UnsafeByteArrayOutputStream;
import org.apache.dubbo.common.json.GsonUtils;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.utils.PojoUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ScopeModelAware;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.rpc.support.ProtocolUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.stream.IntStream;

/**
 * Desc:Dubbo generic filter
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 17:50
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER}, order = -20000)
public class RemoteGenericFilter implements Filter, Filter.Listener, ScopeModelAware {

    private static final String METHOD_NAME_INVOKE = "$invoke";
    private static final String METHOD_NAME_INVOKE_ASYNC = "$invokeAsync";
    private static final String GENERIC_ATTACHMENT = "generic";

    private ApplicationModel applicationModel;

    @Override
    public void setApplicationModel(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        if ((inv.getMethodName().equals(METHOD_NAME_INVOKE) || inv.getMethodName().equals(METHOD_NAME_INVOKE_ASYNC))
                && inv.getArguments() != null
                && inv.getArguments().length == 3
                && !GenericService.class.isAssignableFrom(invoker.getInterface())) {
            String name = ((String)inv.getArguments()[0]).trim();
            String[] types = (String[])inv.getArguments()[1];
            Object[] args = (Object[])inv.getArguments()[2];

            try {
                Method method = ReflectUtils.findMethodByMethodSignature(invoker.getInterface(), name, types);
                Class<?>[] params = method.getParameterTypes();
                if (args == null) {
                    args = new Object[params.length];
                }

                if (types == null) {
                    types = new String[params.length];
                }

                if (args.length != types.length) {
                    throw new RpcException("GenericFilter#invoke args.length != types.length, please check your params");
                } else {
                    String generic = inv.getAttachment(GENERIC_ATTACHMENT);
                    if (StringUtils.isBlank(generic)) {
                        generic = RpcContext.getClientAttachment().getAttachment(GENERIC_ATTACHMENT);
                    }

                    if (!StringUtils.isEmpty(generic) && !ProtocolUtils.isDefaultGenericSerialization(generic) && !ProtocolUtils.isGenericReturnRawResult(generic)) {
                        if (ProtocolUtils.isGsonGenericSerialization(generic)) {
                            args = this.getGsonGenericArgs(args, method.getGenericParameterTypes());
                        } else if (ProtocolUtils.isJavaGenericSerialization(generic)) {
                            Configuration configuration = ApplicationModel.ofNullable(this.applicationModel).getModelEnvironment().getConfiguration();
                            if (!configuration.getBoolean("dubbo.security.serialize.generic.native-java-enable", false)) {
                                String notice = "Trigger the safety barrier! Native Java Serializer is not allowed by default.This means currently maybe being attacking by others. If you are sure this is a mistake, please set `dubbo.security.serialize.generic.native-java-enable` enable in configuration! Before doing so, please make sure you have configure JEP290 to prevent serialization attack.";
                                log.error(notice);
                                throw new RpcException(new IllegalStateException(notice));
                            }

                            for(int i = 0; i < args.length; ++i) {
                                if (byte[].class != args[i].getClass()) {
                                    throw new RpcException("Generic serialization [nativejava] only support message type " + byte[].class + " and your message type is " + args[i].getClass());
                                }

                                try {
                                    UnsafeByteArrayInputStream is = new UnsafeByteArrayInputStream((byte[])args[i]);

                                    try {
                                        args[i] = (this.applicationModel.getExtensionLoader(Serialization.class).getExtension("nativejava")).deserialize((URL)null, is).readObject();
                                    } catch (Throwable var18) {
                                        try {
                                            is.close();
                                        } catch (Throwable var15) {
                                            var18.addSuppressed(var15);
                                        }

                                        throw var18;
                                    }

                                    is.close();
                                } catch (Exception var19) {
                                    throw new RpcException("Deserialize argument [" + (i + 1) + "] failed.", var19);
                                }
                            }
                        } else if (ProtocolUtils.isBeanGenericSerialization(generic)) {
                            for(int i = 0; i < args.length; ++i) {
                                if (!(args[i] instanceof JavaBeanDescriptor)) {
                                    throw new RpcException("Generic serialization [bean] only support message type " + JavaBeanDescriptor.class.getName() + " and your message type is " + args[i].getClass().getName());
                                }

                                args[i] = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor)args[i]);
                            }
                        } else if (ProtocolUtils.isProtobufGenericSerialization(generic)) {
                            if (args.length != 1 || !(args[0] instanceof String)) {
                                throw new RpcException("Generic serialization [protobuf-json] only support one " + String.class.getName() + " argument and your message size is " + args.length + " and type is" + args[0].getClass().getName());
                            }

                            try {
                                UnsafeByteArrayInputStream is = new UnsafeByteArrayInputStream(((String)args[0]).getBytes());

                                try {
                                    args[0] = ((Serialization)this.applicationModel.getExtensionLoader(Serialization.class).getExtension("protobuf-json")).deserialize((URL)null, is).readObject(method.getParameterTypes()[0]);
                                } catch (Throwable var16) {
                                    try {
                                        is.close();
                                    } catch (Throwable var14) {
                                        var16.addSuppressed(var14);
                                    }

                                    throw var16;
                                }

                                is.close();
                            } catch (Exception var17) {
                                throw new RpcException("Deserialize argument failed.", var17);
                            }
                        }
                    } else {
                        try {
                            args = PojoUtils.realize(args, params, method.getGenericParameterTypes());
                        } catch (IllegalArgumentException var20) {
                            throw new RpcException(var20);
                        }
                    }

                    RpcInvocation rpcInvocation = new RpcInvocation(inv.getTargetServiceUniqueName(), invoker.getUrl().getServiceModel(), method.getName(), invoker.getInterface().getName(), invoker.getUrl().getProtocolServiceKey(), method.getParameterTypes(), args, inv.getObjectAttachments(), inv.getInvoker(), inv.getAttributes(), inv instanceof RpcInvocation ? ((RpcInvocation)inv).getInvokeMode() : null);
                    return invoker.invoke(rpcInvocation);
                }
            } catch (ClassNotFoundException | NoSuchMethodException var21) {
                throw new RpcException(var21.getMessage(), var21);
            }
        } else {
            return invoker.invoke(inv);
        }
    }

    private Object[] getGsonGenericArgs(final Object[] args, Type[] types) {
        return IntStream.range(0, args.length).mapToObj((i) -> {
            if (!(args[i] instanceof String)) {
                throw new RpcException("When using GSON to deserialize generic dubbo request arguments, the arguments must be of type String");
            } else {
                String str = args[i].toString();

                try {
                    return GsonUtils.fromJson(str, types[i]);
                } catch (RuntimeException var5) {
                    throw new RpcException(var5.getMessage());
                }
            }
        }).toArray();
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation inv) {
        if ((inv.getMethodName().equals(METHOD_NAME_INVOKE) || inv.getMethodName().equals(METHOD_NAME_INVOKE_ASYNC)) && inv.getArguments() != null && inv.getArguments().length == 3 && !GenericService.class.isAssignableFrom(invoker.getInterface())) {
            String generic = inv.getAttachment(GENERIC_ATTACHMENT);
            if (StringUtils.isBlank(generic)) {
                generic = RpcContext.getClientAttachment().getAttachment(GENERIC_ATTACHMENT);
            }

            if (appResponse.hasException()) {
                Throwable appException = appResponse.getException();

                //业务异常直接返回
                if (appException instanceof RemoteServiceException) {
                    return;
                }

                if (appException instanceof GenericException) {
                    GenericException tmp = (GenericException)appException;
                    appException = new com.alibaba.dubbo.rpc.service.GenericException(tmp.getExceptionClass(), tmp.getExceptionMessage());
                }

                if (!(appException instanceof com.alibaba.dubbo.rpc.service.GenericException)) {
                    appException = new com.alibaba.dubbo.rpc.service.GenericException((Throwable)appException);
                }

                appResponse.setException((Throwable)appException);
            }

            UnsafeByteArrayOutputStream os;
            if (ProtocolUtils.isJavaGenericSerialization(generic)) {
                try {
                    os = new UnsafeByteArrayOutputStream(512);
                    (this.applicationModel.getExtensionLoader(Serialization.class).getExtension("nativejava")).serialize((URL)null, os).writeObject(appResponse.getValue());
                    appResponse.setValue(os.toByteArray());
                } catch (IOException var8) {
                    throw new RpcException("Generic serialization [nativejava] serialize result failed.", var8);
                }
            } else if (ProtocolUtils.isBeanGenericSerialization(generic)) {
                appResponse.setValue(JavaBeanSerializeUtil.serialize(appResponse.getValue(), JavaBeanAccessor.METHOD));
            } else if (ProtocolUtils.isProtobufGenericSerialization(generic)) {
                try {
                    os = new UnsafeByteArrayOutputStream(512);
                    (this.applicationModel.getExtensionLoader(Serialization.class).getExtension("protobuf-json")).serialize((URL)null, os).writeObject(appResponse.getValue());
                    appResponse.setValue(os.toString());
                } catch (IOException var7) {
                    throw new RpcException("Generic serialization [protobuf-json] serialize result failed.", var7);
                }
            } else {
                if (ProtocolUtils.isGenericReturnRawResult(generic)) {
                    return;
                }

                appResponse.setValue(PojoUtils.generalize(appResponse.getValue()));
            }
        }

    }

    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
    }
}
