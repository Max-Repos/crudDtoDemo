package in.rajaram.userLogin.Service;

import in.rajaram.userLogin.DTO.TransferRequest;
import in.rajaram.userLogin.Entity.BankAccount;
import in.rajaram.userLogin.Entity.User;
import in.rajaram.userLogin.Repository.BankAccountRepository;
import in.rajaram.userLogin.Repository.UserDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final UserDetailRepository userDetailRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public @Nullable String transfer(TransferRequest request) {
        validateRequest(request);

        BankAccount senderAccount = getAccount(request.getFrom());
        BankAccount receiverAccount = getAccount(request.getTo());
        validateBankAccount(senderAccount, receiverAccount, request.getAmount());

        transferAmount(senderAccount, receiverAccount, request.getAmount());
        return "Money Transferred Successfully";
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private BankAccount getAccount(Long id) {
        User user = userDetailRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found :" + id));
        return user.getBankAccount();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void transferAmount(BankAccount senderAccount, BankAccount receiverAccount, BigDecimal amount) {

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void validateBankAccount(BankAccount senderAccount, BankAccount receiverAccount, BigDecimal amount) {

        if (senderAccount == null || receiverAccount == null) {
            throw new IllegalArgumentException("Both user must have bank account");
        }

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void validateRequest(TransferRequest request) {
        Long senderId = request.getFrom();
        Long receiverId = request.getTo();
        BigDecimal amount = request.getAmount();

        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("You can not send to yourself");
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
