package com.pet.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileResponseDto extends BaseResponseDto{
    private Long userId;
}
