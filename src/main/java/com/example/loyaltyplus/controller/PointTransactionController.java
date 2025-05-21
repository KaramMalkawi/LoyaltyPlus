package com.example.loyaltyplus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.model.entity.PointTransaction;
import com.example.loyaltyplus.service.PointTransactionService;

@RestController
@RequestMapping("/point-transactions")
public class PointTransactionController {
    @Autowired
    private PointTransactionService pointTransactionService;

    @GetMapping("/all")
    public ResponseEntity<List<PointTransaction>> getAllPointTransactions() {
        List<PointTransaction> pointTransactions = pointTransactionService.getAllPointTransactions();
        return ResponseEntity.ok(pointTransactions);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PointTransaction> getPointTransactionById(@PathVariable Long id) {
        Optional<PointTransaction> pointTransaction = pointTransactionService.getPointTransactionById(id);
        return pointTransaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<PointTransaction> createPointTransaction(@RequestBody PointTransaction pointTransaction) {
        PointTransaction newPointTransaction = pointTransactionService.createPointTransaction(pointTransaction);
        return ResponseEntity.ok(newPointTransaction);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PointTransaction> updatePointTransaction(@PathVariable Long id,
            @RequestBody PointTransaction updatedPointTransaction) {
        PointTransaction updatedTransaction = pointTransactionService.updatePointTransaction(id, updatedPointTransaction);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePointTransaction(@PathVariable Long id) {
        pointTransactionService.deletePointTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
