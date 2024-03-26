package com.example.accountapi.grpc;

import com.example.user.UserInformation;
import com.example.user.UserInformationRequest;
import com.example.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void getUserInformation(UserInformationRequest request, StreamObserver<UserInformation> responseObserver) {

        System.out.println("Request received");
        System.out.println(request.getUserId());

        UserInformation userInformation = UserInformation.newBuilder()
                .setUserId(request.getUserId())
                .setName("hi")
                .build();

        responseObserver.onNext(userInformation);
        responseObserver.onCompleted();

    }
}
