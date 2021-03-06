package com.ss.camper.store.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.WithMockCustomUser;
import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.store.application.StoreProfileImageService;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.domain.StoreType;
import com.ss.camper.store.ui.payload.DeleteStoreProfileImagesPayload;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ss.camper.common.ApiDocumentAttributes.*;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.store.StoreMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest extends ControllerTest {

    @MockBean
    private StoreService storeService;

    @MockBean
    private StoreProfileImageService storeProfileImageService;

    @Test
    @WithMockCustomUser
    void ??????_??????() throws Exception {
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        given(storeService.registerStore(anyLong(), any(StoreDTO.class))).willReturn(storeDTO);

        final RegisterStorePayload.Request request = RegisterStorePayload.Request.builder()
            .storeStatus(STORE_STATUS)
            .storeType(STORE_TYPE)
            .storeName(STORE_NAME)
            .zipCode(ADDRESS.getZipCode())
            .defaultAddress(ADDRESS.getDefaultAddress())
            .detailAddress(ADDRESS.getDetailAddress())
            .latitude(ADDRESS.getLatitude())
            .longitude(ADDRESS.getLongitude())
            .tel(TEL)
            .homepageUrl(HOMEPAGE_URL)
            .reservationUrl(RESERVATION_URL)
            .introduction(INTRODUCTION)
            .openingDays(OPENING_DAYS)
            .openTime(OPEN_TIME)
            .closeTime(CLOSE_TIME)
            .tags(new HashSet<>(){{
                add(TAG_TITLE1);
                add(TAG_TITLE2);
            }})
            .build();

        final ResultActions result = mockMvc.perform(
            post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
            .andDo(document("store/register",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("storeType").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeTypeAttribute()),
                    fieldWithPath("storeStatus").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeStatusAttribute()),
                    fieldWithPath("storeName").type(JsonFieldType.STRING).description("?????? ???"),
                    fieldWithPath("zipCode").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("defaultAddress").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("detailAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("??????"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("??????"),
                    fieldWithPath("tel").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("???????????? URL"),
                    fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("?????? ????????? URL"),
                    fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                    fieldWithPath("openingDays[]").type(JsonFieldType.ARRAY).optional().description("?????????").attributes(openingDaysAttribute()),
                    fieldWithPath("openTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                    fieldWithPath("closeTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                    fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("??????")
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????_??????() throws Exception {
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        given(storeService.modifyStore(anyLong(), anyLong(), any(StoreDTO.class))).willReturn(storeDTO);

        final ModifyStorePayload.Request request = ModifyStorePayload.Request.builder()
            .storeStatus(STORE_STATUS)
            .storeName(STORE_NAME)
            .zipCode(ADDRESS.getZipCode())
            .defaultAddress(ADDRESS.getDefaultAddress())
            .detailAddress(ADDRESS.getDetailAddress())
            .latitude(ADDRESS.getLatitude())
            .longitude(ADDRESS.getLongitude())
            .tel(TEL)
            .homepageUrl(HOMEPAGE_URL)
            .reservationUrl(RESERVATION_URL)
            .introduction(INTRODUCTION)
            .openingDays(OPENING_DAYS)
            .openTime(OPEN_TIME)
            .closeTime(CLOSE_TIME)
            .tags(new HashSet<>(){{
                add(TAG_TITLE1);
                add(TAG_TITLE2);
            }})
            .build();
        final ResultActions result = mockMvc.perform(
                put("/store/{storeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("store/modify",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("storeId").description("?????? ????????????")
                        ),
                        requestFields(
                                fieldWithPath("storeStatus").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeStatusAttribute()),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("?????? ???"),
                                fieldWithPath("zipCode").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("defaultAddress").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("detailAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("tel").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("???????????? URL"),
                                fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("?????? ????????? URL"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                fieldWithPath("openingDays[]").type(JsonFieldType.ARRAY).optional().description("?????????").attributes(openingDaysAttribute()),
                                fieldWithPath("openTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                                fieldWithPath("closeTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                                fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("??????")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????() throws Exception {
        willDoNothing().given(storeService).deleteStore(anyLong(), anyLong());

        final ResultActions result = mockMvc.perform(
                delete("/store/{storeId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("store/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("storeId").description("?????? ????????????")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    void ??????_??????_??????() throws Exception {
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        storeDTO.setProfileImages(new ArrayList<>(){{
            add(UploadFileDTO.builder()
                    .id(1L)
                    .originName("originFileName.jpg")
                    .uploadName("uploadFileName.jpg")
                    .fullPath("https://s3/upload/uploadFileName.jpg")
                    .path("/upload/uploadFileName.jpg")
                    .size(2541)
                    .ext("JPG")
                    .build());
        }});
        given(storeService.getStoreInfo(anyLong())).willReturn(storeDTO);

        final ResultActions result = mockMvc.perform(
                get("/store/{storeId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("store/info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("storeId").description("?????? ????????????")
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("storeType").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeTypeAttribute()),
                                        fieldWithPath("storeStatus").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeStatusAttribute()),
                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("?????? ???"),
                                        fieldWithPath("address").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
                                        fieldWithPath("address.zipCode").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.defaultAddress").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("address.latitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("address.longitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("tel").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("???????????? URL"),
                                        fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("?????? ????????? URL"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("openingDays").type(JsonFieldType.ARRAY).optional().description("?????????").attributes(openingDaysAttribute()),
                                        fieldWithPath("openTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                                        fieldWithPath("closeTime").type(JsonFieldType.STRING).optional().description("?????? ????????????"),
                                        fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("??????") ,
                                        fieldWithPath("tags[].id").type(JsonFieldType.NUMBER).optional().description("?????? ????????????"),
                                        fieldWithPath("tags[].title").type(JsonFieldType.STRING).optional().description("?????? ?????????"),
                                        fieldWithPath("profileImages[]").type(JsonFieldType.ARRAY).optional().description("????????? ?????????"),
                                        fieldWithPath("profileImages[].id").type(JsonFieldType.NUMBER).optional().description("?????? ????????????"),
                                        fieldWithPath("profileImages[].originName").type(JsonFieldType.STRING).optional().description("?????? ?????????"),
                                        fieldWithPath("profileImages[].uploadName").type(JsonFieldType.STRING).optional().description("????????? ?????????"),
                                        fieldWithPath("profileImages[].fullPath").type(JsonFieldType.STRING).optional().description("?????? ?????? ??????"),
                                        fieldWithPath("profileImages[].path").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("profileImages[].size").type(JsonFieldType.NUMBER).optional().description("?????? ?????????"),
                                        fieldWithPath("profileImages[].ext").type(JsonFieldType.STRING).optional().description("?????? ?????????")
                                )
                        )
                ));
    }

    @Test
    void ??????_???_??????_??????_??????() throws Exception {
        final int size = 10;
        final int page = 1;
        final List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        PageDTO<StoreListDTO> storeListPage = new PageDTO<>(storeList, storeList.size(), size, page, 1);
        given(storeService.getStoreListByUserId(anyLong(), anyInt(), anyInt())).willReturn(storeListPage);

        final long userId = 1;
        final ResultActions result = mockMvc.perform(
                get("/store/user/{userId}", 1L)
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("store/list-user",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("userId").description("?????? ????????????")
                        ),
                        requestParameters(
                                parameterWithName("size").description("??? ???????????? ?????? ????????? ???"),
                                parameterWithName("page").description("????????? ?????????")
                        ),
                        responseFields(
                                pagingResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("storeType").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeTypeAttribute()),
                                        fieldWithPath("storeStatus").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeStatusAttribute()),
                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("?????? ???"),
                                        fieldWithPath("address").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
                                        fieldWithPath("address.zipCode").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.defaultAddress").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("address.latitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("address.longitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("tel").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("???????????? URL"),
                                        fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("?????? ????????? URL"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("??????")
                                )
                        )
                ));
    }

    @Test
    void ??????_??????_???_??????_??????_??????() throws Exception {
        final int size = 10;
        final int page = 1;
        final List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        PageDTO<StoreListDTO> storeListPage = new PageDTO<>(storeList, storeList.size(), size, page, 1);
        given(storeService.getStoreListByType(any(StoreType.class), anyInt(), anyInt())).willReturn(storeListPage);

        final ResultActions result = mockMvc.perform(
                get("/store/type/{type}", StoreType.CAMP_GROUND)
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("store/list-type",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("type").description("?????? ??????").attributes(storeTypeAttribute())
                        ),
                        requestParameters(
                                parameterWithName("size").description("??? ???????????? ?????? ????????? ???"),
                                parameterWithName("page").description("????????? ?????????")
                        ),
                        responseFields(
                                pagingResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("storeType").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeTypeAttribute()),
                                        fieldWithPath("storeStatus").type(JsonFieldType.STRING).description("?????? ??????").attributes(storeStatusAttribute()),
                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("?????? ???"),
                                        fieldWithPath("address").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
                                        fieldWithPath("address.zipCode").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.defaultAddress").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("address.latitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("address.longitude").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("tel").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("???????????? URL"),
                                        fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("?????? ????????? URL"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("??????")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ?????????_?????????_??????() throws Exception {
        willDoNothing().given(storeProfileImageService).updateProfileImages(anyLong(), anyLong(), anyList());

        MockMultipartFile multipartFile1 = new MockMultipartFile(
                "files",
                "profileImage1.jpg",
                "image/jpg",
                "uploadFile".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile(
                "files",
                "profileImage2.jpg",
                "image/jpg",
                "uploadFile".getBytes());

        final ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.fileUpload("/store/profile-image/{storeId}", 1)
                        .file(multipartFile1)
                        .file(multipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("store/profile-image",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("storeId").description("?????? ????????????")
                        ),
                        requestParts(
                                partWithName("files").description("?????? ?????????(?????? ????????? ??????)")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ?????????_?????????_??????() throws Exception {
        willDoNothing().given(storeProfileImageService).deleteProfileImages(anyLong(), anyLong(), any(Long[].class));

        final DeleteStoreProfileImagesPayload.Request request =
                new DeleteStoreProfileImagesPayload.Request(new Long[] {1L, 2L});
        final ResultActions result = mockMvc.perform(
                delete("/store/profile-image/{storeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("store/profile-image-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("fileIds[]").type(JsonFieldType.ARRAY).description("????????? ?????? ????????????")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

}