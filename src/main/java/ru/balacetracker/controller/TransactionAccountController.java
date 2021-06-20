package ru.balacetracker.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.balacetracker.security.SecurityConstants;
import ru.balacetracker.service.RestExchangeService;

@RestController
@RequestMapping("/transaction-account")
@RequiredArgsConstructor
public class TransactionAccountController {
    private static final String TRANSACTION_ACCOUNT_CONTROLLER_PATH = "/transaction-account";
    private static final String CREATE_PURSE_PATH = "/create-purse";
    private static final String CREATE_INCOME_PATH = "/create-income";
    private static final String CREATE_OUTCOME_PATH = "/create-outcome";
    private static final String GET_ALL_PURSES_PATH = "/purses/all";
    private static final String GET_ALL_OUTCOME_PATH = "/outcome/all";
    private static final String GET_ALL_INCOME_PATH = "/income/all";
    private static final String DELETE_TRANSACTION_ACCOUNT_PATH = "/delete/%s";
    private static final String UPDATE_TRANSACTION_ACCOUNT_PATH = "/update/%s";

    private final RestExchangeService restExchangeService;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-purse")
    public Object createPurse(
            @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.POST,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + CREATE_PURSE_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-outcome")
    public Object createOutcome(
            @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.POST,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + CREATE_OUTCOME_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-intcome")
    public Object createIntcome(
            @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.POST,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + CREATE_INCOME_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/purses/all")
    public Object getPursesForUser() {
        Object result = restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.GET,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + GET_ALL_PURSES_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/outcome/all")
    public Object getOutcomeForUser() {
        Object result = restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.GET,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + GET_ALL_OUTCOME_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/income/all")
    public Object getIncomeForUser() {
        Object result = restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.GET,
                TRANSACTION_ACCOUNT_CONTROLLER_PATH + GET_ALL_INCOME_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.DELETE,
                String.format(TRANSACTION_ACCOUNT_CONTROLLER_PATH + DELETE_TRANSACTION_ACCOUNT_PATH, id),
                Object.class
        );

    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping("/update/{transactionAccountId}")
    public void update(@PathVariable Long transactionAccountId,
                       @RequestBody @NotNull Object body) {
        restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(TRANSACTION_ACCOUNT_CONTROLLER_PATH + UPDATE_TRANSACTION_ACCOUNT_PATH,
                        transactionAccountId),
                Object.class
        );

    }





}
