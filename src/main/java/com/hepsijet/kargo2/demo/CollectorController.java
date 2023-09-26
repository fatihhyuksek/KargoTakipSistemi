package com.hepsijet.kargo2.demo;

import com.hepsijet.kargo2.Delivery.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Collector-controller")
@RequiredArgsConstructor
public class CollectorController {

    private final DeliveryService deliveryService;

    @Operation(description = "Patch delivery endpoint for collector ",
            summary = "Collector rolündeki kullanıcıların kargoyu zimmet aldıklarını belirtmeye yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200"
                    )

            })
    @PatchMapping(value = "/status/collecting")
    public String updateStatusCollecting(Long barcode, String  status) {
        return deliveryService.updateStatusCollecting(barcode,status);

    }
}
