package ru.balacetracker.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.balacetracker.security.SecurityConstants;
import ru.balacetracker.security.utils.SecurityUtils;
import ru.balacetracker.service.RestExchangeService;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
@Slf4j
public class TransactionController {
    private static final String CREATE_TRANSACTION_PATH = "/transaction/create";
    private static final String CREATE_PERIODIC_TRANSACTION_PATH = "/create-periodic/%s";
    private static final String UPDATE_TRANSACTION_PATH = "/transaction/update/%s";
    private static final String DELETE_TRANSACTION_PATH = "/transaction/delete/%s";
    private static final String TRANSACTIONS_FOR_USER_PATH = "/transaction/transactions-for-user/%s/%s/%s";

    private final RestExchangeService restExchangeService;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create")
    public Object createTransaction(
            @RequestBody @NotNull Object body) {
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.POST,
                CREATE_TRANSACTION_PATH,
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/transactions-for-user/{itemsPerPage}/{pageNumber}")
    public Object getTransactionsForUser(@PathVariable String userId,
                                                    @PathVariable Integer itemsPerPage,
                                                    @PathVariable Integer pageNumber,
                                                    @RequestBody @NotNull Object body){
        Object result = restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.GET,
                String.format(TRANSACTIONS_FOR_USER_PATH, SecurityUtils.getCurrentUser().getId(), itemsPerPage,pageNumber),
                Object.class
        );
        return result;
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/delete/{transactionId}")
    public void deleteTransaction(
            @PathVariable Long transactionId){
        restExchangeService.exchangeWithCrud(
                null,
                HttpMethod.DELETE,
                String.format(DELETE_TRANSACTION_PATH, transactionId),
                Object.class
        );
    }

    @PutMapping("/update/{transactionId}")
    public void update(@PathVariable Long transactionId,
                       @RequestBody @NotNull Object body){
        restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(UPDATE_TRANSACTION_PATH, transactionId),
                Object.class
        );
    }

    @PostMapping("/create-periodic/{timePeriod}")
    public Object createPeriodicTransaction(@PathVariable Long timePeriod,
                                          @RequestBody @NotNull Object body){
        return restExchangeService.exchangeWithCrud(
                body,
                HttpMethod.PUT,
                String.format(CREATE_PERIODIC_TRANSACTION_PATH, timePeriod),
                Object.class
        );
    }

}
