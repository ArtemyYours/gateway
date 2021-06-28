package ru.balacetracker.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.balacetracker.exceptions.HttpException;
import ru.balacetracker.exceptions.MessageCode;
import ru.balacetracker.repository.TransactionAccountRepository;
import ru.balacetracker.repository.TransactionRepository;
import ru.balacetracker.security.utils.SecurityUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccessValidator implements ConstraintValidator<ValidAccess, Object> {

    private final TransactionRepository transactionRepository;
    private final TransactionAccountRepository transactionAccountRepository;

    private ValidAccess.Type type;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }

    @Override
    public void initialize(ValidAccess constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    public void checkValidity(Object object, ValidAccess.Type type) {
        if (object instanceof Iterable) {
            ((Iterable<?>) object).forEach(e -> checkValidity(e, type));
        }

        if (type == ValidAccess.Type.TRANSACTION_ID && object instanceof Long) {
            if (!isValidTransactionId((Long) object)){
                throw new HttpException(HttpStatus.FORBIDDEN,
                        MessageCode.ACCESS_DENIED,
                        null,
                        String.format("Transaction %s does not accessible for current user", object));
            }
        }

        if(type == ValidAccess.Type.TRANSACTION_ACCOUNT_ID && object instanceof Long){
            if(isValidTransactionAccountId((Long) object)){
                throw new HttpException(HttpStatus.FORBIDDEN,
                        MessageCode.ACCESS_DENIED,
                        null,
                        String.format("Transaction account %s does not accessible for current user", object));
            }
        }
    }


    private boolean isValidTransactionId(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new HttpException(HttpStatus.NOT_FOUND,
                    MessageCode.ENTITY_NOT_FOUND,
                    null,
                    String.format("Transaction %s does not exist", transactionId));
        }
        String userId = SecurityUtils.getCurrentUser().getId();
        return transactionRepository.isAccessible(userId, transactionId);
    }

    private boolean isValidTransactionAccountId(Long transactionAccountId){
        if (!transactionAccountRepository.existsById(transactionAccountId)) {
            throw new HttpException(HttpStatus.NOT_FOUND,
                    MessageCode.ENTITY_NOT_FOUND,
                    null,
                    String.format("Transaction account %s does not exist", transactionAccountId));
        }
        String userId = SecurityUtils.getCurrentUser().getId();
        return transactionAccountRepository.isTransactionAccountAccessible(userId, transactionAccountId);
    }
}
