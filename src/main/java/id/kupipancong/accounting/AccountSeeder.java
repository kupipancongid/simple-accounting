package id.kupipancong.accounting;

import id.kupipancong.accounting.entity.Account;
import id.kupipancong.accounting.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountSeeder implements CommandLineRunner {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public void run(String... args) throws Exception {
//        accountRepository.save(new Account(null, "1", "1", "Aset"));
//        accountRepository.save(new Account(null, "11", "11", "Kas"));
//        accountRepository.save(new Account(null, "12", "12", "Aset Tetap"));
//
//        accountRepository.save(new Account(null, "2", "2", "Liabilitas"));
//        accountRepository.save(new Account(null, "21", "21", "Hutang Usaha"));
//        accountRepository.save(new Account(null, "22", "22", "Hutang Lancar"));
//
//        accountRepository.save(new Account(null, "3", "3", "Ekuitas"));
//        accountRepository.save(new Account(null, "31", "31", "Modal Pemilik"));
//        accountRepository.save(new Account(null, "32", "32", "Laba Ditahan"));
//
//        accountRepository.save(new Account(null, "4", "4", "Pendapatan"));
//        accountRepository.save(new Account(null, "41", "41", "Penjualan Produk"));
//        accountRepository.save(new Account(null, "42", "42", "Pendapatan Jasa"));
//
//        accountRepository.save(new Account(null, "5", "5", "Biaya"));
//        accountRepository.save(new Account(null, "51", "51", "Biaya"));
//        accountRepository.save(new Account(null, "52", "52", "Biaya"));
    }
}
