package cn.gasin.api.http.register;

import cn.gasin.api.http.Response;
import cn.gasin.api.http.ResponseStatus;
import cn.gasin.api.server.InstanceInfo;
import cn.gasin.api.server.InstanceInfoChangedHolder;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRegistryResponse extends Response {
    List<InstanceInfoChangedHolder> deltaInstanceInfoList;
    Map<String, Map<String, InstanceInfo>> instanceInfoMap;


    public static QueryRegistryResponse success(String message) {
        QueryRegistryResponse response = new QueryRegistryResponse();
        response.setMessage(message);
        response.setStatus(ResponseStatus.SUCCESS);
        return response;
    }

    public static QueryRegistryResponse failed(String message) {
        QueryRegistryResponse response = new QueryRegistryResponse();
        response.setMessage(message);
        response.setStatus(ResponseStatus.FAILED);
        return response;
    }

}
