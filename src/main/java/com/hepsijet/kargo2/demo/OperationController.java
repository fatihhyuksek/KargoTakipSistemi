package com.hepsijet.kargo2.demo;

import com.hepsijet.kargo2.Delivery.DeliveryService;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.JWT.JwtService;
import com.hepsijet.kargo2.JWT.config.JwtAuthenticationFilter;
import com.hepsijet.kargo2.JWT.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/Operation-controller")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OperationController {
    private final DeliveryService deliveryService;
    private final EmployeeService employeeService;
    private final JwtService jwtService;
    private SecurityConfiguration securityConfiguration;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("/login")
    @Hidden
    public ResponseEntity<String> sayHello(HttpServletRequest request, Authentication authentication) {

            org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().toString().equals("[OPERATION]")) {
                return ResponseEntity.ok("Hello " + auth.getName());
            } else if (auth.getAuthorities().toString().equals("[COURIER]")) {
                return ResponseEntity.ok("Bu endpointe erişim yetkiniz yok.");

            }
        return ResponseEntity.ok(String.valueOf(HttpStatus.resolve(403)));
    }

    @Operation(description = "Get delivery endpoint for operation ",
                summary = "Operation rolündeki kulalnıcıların çalıştıkları xdocktaki tüm siparişleri görüntülemeye yarar.",
                responses = {
                        @ApiResponse(
                                description = "Unauthorized / Invalid Token",
                                responseCode = "403"
                        )

                })
    @GetMapping("/getDeliverys")
    @PreAuthorize("hasAuthority('ROLE_OPERATION')")
    public ResponseEntity<?> getDeliveryWithOperationWorkingXdock( )
    {
            return ResponseEntity.ok(deliveryService.getDeliveryWithOperationWorkingXdock());
    }

    @Operation(description = "Patch delivery endpoint for operation ",
            summary = "Operation rolündeki kullanıcıların kargonun transfer merkezine geldiğini onaylamaya yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })
    @PatchMapping(value = "/delivery/inTransferCenter/current_xdock")
    public String deliveryInTransferCenter(Long barcode) {
            return deliveryService.deliveryInTransferCenter(barcode);


    }
    @Operation(description = "Patch delivery endpoint for operation ",
            summary = "Operation rolündeki kullanıcıların kargonun gideceğe xdock noktasına geldiğini onaylamaya yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })
    @PatchMapping(value = "/delivery/inXdock/current_xdock")
    public String deliveryInXdock(Long barcode) {
            return deliveryService.deliveryInXdock(barcode);



    }
    @Operation(description = "Patch delivery endpoint for operation ",
            summary = "Operation rolündeki kullanıcıların kargoyu kuryenin üzerine atamaya yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })

    @PatchMapping(value = "/delivery/assing/courier")
    public String assingEmployee (Long barcode){
            return deliveryService.assingEmployee(barcode);


    }

    @Operation(description = "Patch delivery endpoint for operation ",
            summary = "Operation rolündeki kullanıcıların kargonun xdock'a gelidğini belirtmeye yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })
    @PatchMapping(value = "/assing/xdock")
    public String  assingXdock(Long barcode) {
        return deliveryService.assingXdock(barcode);

    }


    @Operation(description = "Patch delivery endpoint for operation ",
            summary = "Operation rolündeki kullanıcıların transfer merkezine gelen kargoların xdock'a gidicek kargoların statüsünü transfering yapar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })
    @PatchMapping(value = "/status/transfering")
    public String updateStatusTransfering(Long barcode, String status) {
        return deliveryService.updateStatusTransfering(barcode, status);
    }

}
