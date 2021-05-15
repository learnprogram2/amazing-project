package cn.gasin.dfs.rpc.namenode.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * nameNode backup api
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: NameNodeRpcServer.proto")
public final class NameNodeBackupAPIGrpc {

  private NameNodeBackupAPIGrpc() {}

  public static final String SERVICE_NAME = "cn.gasin.dfs.rpc.namenode.NameNodeBackupAPI";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest,
      cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> getFetchEditLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "fetchEditLog",
      requestType = cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest.class,
      responseType = cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest,
      cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> getFetchEditLogMethod() {
    io.grpc.MethodDescriptor<cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest, cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> getFetchEditLogMethod;
    if ((getFetchEditLogMethod = NameNodeBackupAPIGrpc.getFetchEditLogMethod) == null) {
      synchronized (NameNodeBackupAPIGrpc.class) {
        if ((getFetchEditLogMethod = NameNodeBackupAPIGrpc.getFetchEditLogMethod) == null) {
          NameNodeBackupAPIGrpc.getFetchEditLogMethod = getFetchEditLogMethod =
              io.grpc.MethodDescriptor.<cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest, cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "fetchEditLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeBackupAPIMethodDescriptorSupplier("fetchEditLog"))
              .build();
        }
      }
    }
    return getFetchEditLogMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NameNodeBackupAPIStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIStub>() {
        @java.lang.Override
        public NameNodeBackupAPIStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeBackupAPIStub(channel, callOptions);
        }
      };
    return NameNodeBackupAPIStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NameNodeBackupAPIBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIBlockingStub>() {
        @java.lang.Override
        public NameNodeBackupAPIBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeBackupAPIBlockingStub(channel, callOptions);
        }
      };
    return NameNodeBackupAPIBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NameNodeBackupAPIFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeBackupAPIFutureStub>() {
        @java.lang.Override
        public NameNodeBackupAPIFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeBackupAPIFutureStub(channel, callOptions);
        }
      };
    return NameNodeBackupAPIFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * nameNode backup api
   * </pre>
   */
  public static abstract class NameNodeBackupAPIImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * editsLog fetch api
     * </pre>
     */
    public void fetchEditLog(cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest request,
        io.grpc.stub.StreamObserver<cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchEditLogMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFetchEditLogMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest,
                cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse>(
                  this, METHODID_FETCH_EDIT_LOG)))
          .build();
    }
  }

  /**
   * <pre>
   * nameNode backup api
   * </pre>
   */
  public static final class NameNodeBackupAPIStub extends io.grpc.stub.AbstractAsyncStub<NameNodeBackupAPIStub> {
    private NameNodeBackupAPIStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeBackupAPIStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeBackupAPIStub(channel, callOptions);
    }

    /**
     * <pre>
     * editsLog fetch api
     * </pre>
     */
    public void fetchEditLog(cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest request,
        io.grpc.stub.StreamObserver<cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFetchEditLogMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * nameNode backup api
   * </pre>
   */
  public static final class NameNodeBackupAPIBlockingStub extends io.grpc.stub.AbstractBlockingStub<NameNodeBackupAPIBlockingStub> {
    private NameNodeBackupAPIBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeBackupAPIBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeBackupAPIBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * editsLog fetch api
     * </pre>
     */
    public cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse fetchEditLog(cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFetchEditLogMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * nameNode backup api
   * </pre>
   */
  public static final class NameNodeBackupAPIFutureStub extends io.grpc.stub.AbstractFutureStub<NameNodeBackupAPIFutureStub> {
    private NameNodeBackupAPIFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeBackupAPIFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeBackupAPIFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * editsLog fetch api
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse> fetchEditLog(
        cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFetchEditLogMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FETCH_EDIT_LOG = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NameNodeBackupAPIImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NameNodeBackupAPIImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FETCH_EDIT_LOG:
          serviceImpl.fetchEditLog((cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest) request,
              (io.grpc.stub.StreamObserver<cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class NameNodeBackupAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NameNodeBackupAPIBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cn.gasin.dfs.rpc.namenode.service.NameNodeServer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NameNodeBackupAPI");
    }
  }

  private static final class NameNodeBackupAPIFileDescriptorSupplier
      extends NameNodeBackupAPIBaseDescriptorSupplier {
    NameNodeBackupAPIFileDescriptorSupplier() {}
  }

  private static final class NameNodeBackupAPIMethodDescriptorSupplier
      extends NameNodeBackupAPIBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NameNodeBackupAPIMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NameNodeBackupAPIGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NameNodeBackupAPIFileDescriptorSupplier())
              .addMethod(getFetchEditLogMethod())
              .build();
        }
      }
    }
    return result;
  }
}
