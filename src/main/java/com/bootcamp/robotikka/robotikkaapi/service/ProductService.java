package com.bootcamp.robotikka.robotikkaapi.service;

import com.bootcamp.robotikka.robotikkaapi.dto.paginated.PaginatedProductDTO;
import com.bootcamp.robotikka.robotikkaapi.dto.request.RequestProductDTO;
import com.bootcamp.robotikka.robotikkaapi.dto.response.CommonResponseDTO;
import com.bootcamp.robotikka.robotikkaapi.dto.response.ResponseProductDTO;

public interface ProductService {
    public CommonResponseDTO createProduct(RequestProductDTO dto);
    public ResponseProductDTO findProduct(String id);

    public CommonResponseDTO updateProduct(RequestProductDTO dto, String id);

    public CommonResponseDTO deleteProduct(String id);

    PaginatedProductDTO findAll(String text, int page, int size);


}
