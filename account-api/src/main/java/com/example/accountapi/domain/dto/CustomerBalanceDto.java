package com.example.accountapi.domain.dto;

import com.example.accountapi.domain.model.CustomerBalanceHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBalanceDto {


    @Schema(description = "변경된 금액", example = "1000")
    private Integer changedMoney;
    @Schema(description = "현재 잔액", example = "1000")
    private Integer currentMoney;


 public static CustomerBalanceDto from(CustomerBalanceHistory history){
        return CustomerBalanceDto.builder()
                .changedMoney(history.getChangedMoney())
                .currentMoney(history.getCurrentMoney())
                .build();
 }
 public static CustomerBalanceDto currentMoneyFrom(CustomerBalanceHistory history) {
     return CustomerBalanceDto.builder()
             .currentMoney(history.getCurrentMoney())
             .build();
 }
}
