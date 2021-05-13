// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcServer.proto

package cn.gasin.dfs.rpc.namenode.service;

public final class NameNodeServer {
  private NameNodeServer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_RegisterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_RegisterResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_MkdirResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gasin_dfs_rpc_namenode_MkdirRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027NameNodeRpcServer.proto\022\031cn.gasin.dfs." +
      "rpc.cn.gasin.dfs.namenode\"=\n\017RegisterRequest\022\n\n\002ip\030\001 " +
      "\001(\t\022\014\n\004port\030\002 \001(\005\022\020\n\010hostname\030\003 \001(\t\">\n\020H" +
      "eartbeatRequest\022\n\n\002ip\030\001 \001(\t\022\014\n\004port\030\002 \001(" +
      "\005\022\020\n\010hostname\030\003 \001(\t\"3\n\020RegisterResponse\022" +
      "\016\n\006status\030\001 \001(\005\022\017\n\007message\030\002 \001(\t\"4\n\021Hear" +
      "tbeatResponse\022\016\n\006status\030\001 \001(\005\022\017\n\007message" +
      "\030\002 \001(\t\"0\n\rMkdirResponse\022\016\n\006status\030\001 \001(\005\022" +
      "\017\n\007message\030\002 \001(\t\"\034\n\014MkdirRequest\022\014\n\004path" +
      "\030\001 \001(\t2\345\001\n\022ClusterMaintainAPI\022e\n\010registe" +
      "r\022*.cn.gasin.dfs.rpc.cn.gasin.dfs.namenode.RegisterRe" +
      "quest\032+.cn.gasin.dfs.rpc.cn.gasin.dfs.namenode.Regist" +
      "erResponse\"\000\022h\n\theartbeat\022+.cn.gasin.dfs" +
      ".rpc.cn.gasin.dfs.namenode.HeartbeatRequest\032,.cn.gasi" +
      "n.dfs.rpc.cn.gasin.dfs.namenode.HeartbeatResponse\"\0002i" +
      "\n\tClientAPI\022\\\n\005mkdir\022\'.cn.gasin.dfs.rpc." +
      "cn.gasin.dfs.namenode.MkdirRequest\032(.cn.gasin.dfs.rpc" +
      ".cn.gasin.dfs.namenode.MkdirResponse\"\000B5\n!cn.gasin.df" +
      "s.rpc.cn.gasin.dfs.namenode.serviceB\016NameNodeServerP\001" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_RegisterRequest_descriptor,
        new String[] { "Ip", "Port", "Hostname", });
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatRequest_descriptor,
        new String[] { "Ip", "Port", "Hostname", });
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_cn_gasin_dfs_rpc_namenode_RegisterResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_RegisterResponse_descriptor,
        new String[] { "Status", "Message", });
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_HeartbeatResponse_descriptor,
        new String[] { "Status", "Message", });
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_MkdirResponse_descriptor,
        new String[] { "Status", "Message", });
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_cn_gasin_dfs_rpc_namenode_MkdirRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gasin_dfs_rpc_namenode_MkdirRequest_descriptor,
        new String[] { "Path", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
