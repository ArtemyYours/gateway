package ru.balacetracker.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.balacetracker.security.SecurityConstants;
import ru.balacetracker.service.RestExchangeService;
import ru.balacetracker.validation.ValidAccess;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private static final String TRANSACTION_CONTROLLER_PATH = "/transaction";
    private static final String CREATE_TRANSACTION_PATH = "/create";
    private static final String CREATE_PERIODIC_TRANSACTION_PATH = "/create-periodic/%s";
    private static final String UPDATE_TRANSACTION_PATH = "/update/%s";
    private static final String DELETE_TRANSACTION_PATH = "/delete/%s";
    private static final String TRANSACTIONS_FOR_USER_PATH = "/transactions-for-user/%s/%s";

    private final RestExchangeService restExchangeService;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create")
    public Object createTransaction(
            @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.POST,
                TRANSACTION_CONTROLLER_PATH + CREATE_TRANSACTION_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping ("/transactions-for-user/{itemsPerPage}/{pageNumber}")
    public Object getTransactionsForUser(@PathVariable Integer itemsPerPage,
                                         @PathVariable Integer pageNumber,
                                         @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(TRANSACTION_CONTROLLER_PATH + TRANSACTIONS_FOR_USER_PATH, itemsPerPage, pageNumber),
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/delete/{transactionId}")
    public void deleteTransaction(
            @PathVariable @NotNull @Positive @ValidAccess(type = ValidAccess.Type.TRANSACTION_ID) Long transactionId) {
        restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.DELETE,
                String.format(TRANSACTION_CONTROLLER_PATH + DELETE_TRANSACTION_PATH, transactionId),
                Object.class
        );
    }

    @PutMapping("/update/{transactionId}")
    public void update(@PathVariable @NotNull @Positive @ValidAccess(type = ValidAccess.Type.TRANSACTION_ID) Long transactionId,
                       @RequestBody @NotNull Object body) {
        restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(TRANSACTION_CONTROLLER_PATH + UPDATE_TRANSACTION_PATH, transactionId),
                Object.class
        );
    }

    @PostMapping("/create-periodic/{timePeriod}")
    public Object createPeriodicTransaction(@PathVariable Long timePeriod,
                                            @RequestBody @NotNull Object body) {
        return restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(TRANSACTION_CONTROLLER_PATH + CREATE_PERIODIC_TRANSACTION_PATH, timePeriod),
                Object.class
        );
    }

}
