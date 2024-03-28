//package com.example.orderapi.gRPC;
//
//import com.example.user.UserInformation;
//import com.example.user.UserInformationRequest;
//import com.example.user.UserServiceGrpc;
//import net.devh.boot.grpc.client.inject.GrpcClient;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @GrpcClient("user-service")
//    private UserServiceGrpc.UserServiceBlockingStub userClient;
//
//    public UserInformation getUserInformation(int userId) {
//        System.out.println("service in???");
//        UserInformationRequest request = UserInformationRequest
//                .newBuilder()
//                .setUserId(userId)
//                .build();
//        UserInformation userInfo = this.userClient.getUserInformation(request);
//
//
//        return userInfo;
//    }
//}
