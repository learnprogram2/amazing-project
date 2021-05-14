package cn.gasin.dfs.rpc.namenode.grpc;

import cn.gasin.dfs.rpc.namenode.service.*;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * cluster maintain api
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: NameNodeRpcServer.proto")
public final class ClusterMaintainAPIGrpc {

  private ClusterMaintainAPIGrpc() {}

  public static final String SERVICE_NAME = "cn.gasin.dfs.rpc.namenode.ClusterMaintainAPI";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<RegisterRequest,
          RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "register",
      requestType = RegisterRequest.class,
      responseType = RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<RegisterRequest,
      RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<RegisterRequest, RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = ClusterMaintainAPIGrpc.getRegisterMethod) == null) {
      synchronized (ClusterMaintainAPIGrpc.class) {
        if ((getRegisterMethod = ClusterMaintainAPIGrpc.getRegisterMethod) == null) {
          ClusterMaintainAPIGrpc.getRegisterMethod = getRegisterMethod =
              io.grpc.MethodDescriptor.<RegisterRequest, RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClusterMaintainAPIMethodDescriptorSupplier("register"))
              .build();
        }
      }
    }
    return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<HeartbeatRequest,
          HeartbeatResponse> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "heartbeat",
      requestType = HeartbeatRequest.class,
      responseType = HeartbeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<HeartbeatRequest,
      HeartbeatResponse> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<HeartbeatRequest, HeartbeatResponse> getHeartbeatMethod;
    if ((getHeartbeatMethod = ClusterMaintainAPIGrpc.getHeartbeatMethod) == null) {
      synchronized (ClusterMaintainAPIGrpc.class) {
        if ((getHeartbeatMethod = ClusterMaintainAPIGrpc.getHeartbeatMethod) == null) {
          ClusterMaintainAPIGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<HeartbeatRequest, HeartbeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HeartbeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HeartbeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClusterMaintainAPIMethodDescriptorSupplier("heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClusterMaintainAPIStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIStub>() {
        @Override
        public ClusterMaintainAPIStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterMaintainAPIStub(channel, callOptions);
        }
      };
    return ClusterMaintainAPIStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClusterMaintainAPIBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIBlockingStub>() {
        @Override
        public ClusterMaintainAPIBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterMaintainAPIBlockingStub(channel, callOptions);
        }
      };
    return ClusterMaintainAPIBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClusterMaintainAPIFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClusterMaintainAPIFutureStub>() {
        @Override
        public ClusterMaintainAPIFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClusterMaintainAPIFutureStub(channel, callOptions);
        }
      };
    return ClusterMaintainAPIFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * cluster maintain api
   * </pre>
   */
  public static abstract class ClusterMaintainAPIImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public void register(RegisterRequest request,
                         io.grpc.stub.StreamObserver<RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     * <pre>
     * heartbeat api
     * </pre>
     */
    public void heartbeat(HeartbeatRequest request,
                          io.grpc.stub.StreamObserver<HeartbeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                RegisterRequest,
                RegisterResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getHeartbeatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                HeartbeatRequest,
                HeartbeatResponse>(
                  this, METHODID_HEARTBEAT)))
          .build();
    }
  }

  /**
   * <pre>
   * cluster maintain api
   * </pre>
   */
  public static final class ClusterMaintainAPIStub extends io.grpc.stub.AbstractAsyncStub<ClusterMaintainAPIStub> {
    private ClusterMaintainAPIStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClusterMaintainAPIStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterMaintainAPIStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public void register(RegisterRequest request,
                         io.grpc.stub.StreamObserver<RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * heartbeat api
     * </pre>
     */
    public void heartbeat(HeartbeatRequest request,
                          io.grpc.stub.StreamObserver<HeartbeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * cluster maintain api
   * </pre>
   */
  public static final class ClusterMaintainAPIBlockingStub extends io.grpc.stub.AbstractBlockingStub<ClusterMaintainAPIBlockingStub> {
    private ClusterMaintainAPIBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClusterMaintainAPIBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterMaintainAPIBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public RegisterResponse register(RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * heartbeat api
     * </pre>
     */
    public HeartbeatResponse heartbeat(HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * cluster maintain api
   * </pre>
   */
  public static final class ClusterMaintainAPIFutureStub extends io.grpc.stub.AbstractFutureStub<ClusterMaintainAPIFutureStub> {
    private ClusterMaintainAPIFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClusterMaintainAPIFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClusterMaintainAPIFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<RegisterResponse> register(
        RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * heartbeat api
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<HeartbeatResponse> heartbeat(
        HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;
  private static final int METHODID_HEARTBEAT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ClusterMaintainAPIImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ClusterMaintainAPIImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((RegisterRequest) request,
              (io.grpc.stub.StreamObserver<RegisterResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<HeartbeatResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ClusterMaintainAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClusterMaintainAPIBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return NameNodeServer.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClusterMaintainAPI");
    }
  }

  private static final class ClusterMaintainAPIFileDescriptorSupplier
      extends ClusterMaintainAPIBaseDescriptorSupplier {
    ClusterMaintainAPIFileDescriptorSupplier() {}
  }

  private static final class ClusterMaintainAPIMethodDescriptorSupplier
      extends ClusterMaintainAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ClusterMaintainAPIMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ClusterMaintainAPIGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClusterMaintainAPIFileDescriptorSupplier())
              .addMethod(getRegisterMethod())
              .addMethod(getHeartbeatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
