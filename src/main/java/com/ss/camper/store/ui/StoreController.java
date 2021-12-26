package com.ss.camper.store.ui;

import com.ss.camper.common.payload.ApiResponse;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.ui.payload.GetStorePayload;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping(name = "매장 등록")
    public ApiResponse registerStore(@Valid @RequestBody RegisterStorePayload.Request request) {
        storeService.register(request.convertStoreDTO());
        return new ApiResponse();
    }

    @PutMapping(name = "매장 정보 수정", value = "{storeId}")
    public ApiResponse modifyStore(@PathVariable long storeId, @Valid @RequestBody ModifyStorePayload.Request request) {
        storeService.modify(storeId, request.convertStoreDTO());
        return new ApiResponse();
    }

    @GetMapping(name = "매장 정보 조회", value = "{storeId}")
    public DataApiResponse<GetStorePayload.Response> getStore(@PathVariable long storeId) {
        final StoreDTO storeDTO = storeService.getInfo(storeId);
        return new DataApiResponse<>(new GetStorePayload.Response(storeDTO));
    }

    @GetMapping(name = "매장 목록 조회")
    public void getStoreList() {

    }

}