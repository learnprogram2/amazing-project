package cn.gasin.dfs.rpc.namenode.grpc;

import cn.gasin.dfs.rpc.namenode.service.MkdirRequest;
import cn.gasin.dfs.rpc.namenode.service.MkdirResponse;
import cn.gasin.dfs.rpc.namenode.service.NameNodeServer;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * client communicate api
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: NameNodeRpcServer.proto")
public final class ClientAPIGrpc {

  private ClientAPIGrpc() {}

  public static final String SERVICE_NAME = "cn.gasin.dfs.rpc.cn.gasin.dfs.namenode.ClientAPI";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<MkdirRequest,
          MkdirResponse> getMkdirMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "mkdir",
      requestType = MkdirRequest.class,
      responseType = MkdirResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MkdirRequest,
      MkdirResponse> getMkdirMethod() {
    io.grpc.MethodDescriptor<MkdirRequest, MkdirResponse> getMkdirMethod;
    if ((getMkdirMethod = ClientAPIGrpc.getMkdirMethod) == null) {
      synchronized (ClientAPIGrpc.class) {
        if ((getMkdirMethod = ClientAPIGrpc.getMkdirMethod) == null) {
          ClientAPIGrpc.getMkdirMethod = getMkdirMethod =
              io.grpc.MethodDescriptor.<MkdirRequest, MkdirResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "mkdir"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MkdirRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MkdirResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClientAPIMethodDescriptorSupplier("mkdir"))
              .build();
        }
      }
    }
    return getMkdirMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClientAPIStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientAPIStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientAPIStub>() {
        @Override
        public ClientAPIStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientAPIStub(channel, callOptions);
        }
      };
    return ClientAPIStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClientAPIBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientAPIBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientAPIBlockingStub>() {
        @Override
        public ClientAPIBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientAPIBlockingStub(channel, callOptions);
        }
      };
    return ClientAPIBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClientAPIFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientAPIFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientAPIFutureStub>() {
        @Override
        public ClientAPIFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientAPIFutureStub(channel, callOptions);
        }
      };
    return ClientAPIFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * client communicate api
   * </pre>
   */
  public static abstract class ClientAPIImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public void mkdir(MkdirRequest request,
                      io.grpc.stub.StreamObserver<MkdirResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMkdirMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMkdirMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                MkdirRequest,
                MkdirResponse>(
                  this, METHODID_MKDIR)))
          .build();
    }
  }

  /**
   * <pre>
   * client communicate api
   * </pre>
   */
  public static final class ClientAPIStub extends io.grpc.stub.AbstractAsyncStub<ClientAPIStub> {
    private ClientAPIStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClientAPIStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientAPIStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public void mkdir(MkdirRequest request,
                      io.grpc.stub.StreamObserver<MkdirResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMkdirMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * client communicate api
   * </pre>
   */
  public static final class ClientAPIBlockingStub extends io.grpc.stub.AbstractBlockingStub<ClientAPIBlockingStub> {
    private ClientAPIBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClientAPIBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientAPIBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public MkdirResponse mkdir(MkdirRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMkdirMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * client communicate api
   * </pre>
   */
  public static final class ClientAPIFutureStub extends io.grpc.stub.AbstractFutureStub<ClientAPIFutureStub> {
    private ClientAPIFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ClientAPIFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientAPIFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register api
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<MkdirResponse> mkdir(
        MkdirRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMkdirMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MKDIR = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ClientAPIImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ClientAPIImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MKDIR:
          serviceImpl.mkdir((MkdirRequest) request,
              (io.grpc.stub.StreamObserver<MkdirResponse>) responseObserver);
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

  private static abstract class ClientAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClientAPIBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return NameNodeServer.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClientAPI");
    }
  }

  private static final class ClientAPIFileDescriptorSupplier
      extends ClientAPIBaseDescriptorSupplier {
    ClientAPIFileDescriptorSupplier() {}
  }

  private static final class ClientAPIMethodDescriptorSupplier
      extends ClientAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ClientAPIMethodDescriptorSupplier(String methodName) {
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
      synchronized (ClientAPIGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClientAPIFileDescriptorSupplier())
              .addMethod(getMkdirMethod())
              .build();
        }
      }
    }
    return result;
  }
}
