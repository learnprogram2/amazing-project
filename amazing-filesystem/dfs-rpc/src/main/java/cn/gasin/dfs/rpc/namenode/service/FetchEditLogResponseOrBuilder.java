// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcServer.proto

package cn.gasin.dfs.rpc.namenode.service;

public interface FetchEditLogResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.gasin.dfs.rpc.namenode.FetchEditLogResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 status = 1;</code>
   * @return The status.
   */
  int getStatus();

  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>string editLogJson = 3;</code>
   * @return The editLogJson.
   */
  java.lang.String getEditLogJson();
  /**
   * <code>string editLogJson = 3;</code>
   * @return The bytes for editLogJson.
   */
  com.google.protobuf.ByteString
      getEditLogJsonBytes();
}
