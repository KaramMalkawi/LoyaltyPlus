package com.example.loyaltyplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.model.dto.PointTransactionDto;
import com.example.loyaltyplus.model.entity.PointTransaction;
import com.example.loyaltyplus.service.PointTransactionService;

@RestController
@RequestMapping("/api/point-transactions")
public class PointTransactionController {

	@Autowired
	private final PointTransactionService pointTransactionService;

	public PointTransactionController(PointTransactionService pointTransactionService) {
		this.pointTransactionService = pointTransactionService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<PointTransaction>> getAllPointTransactions() {
		return ResponseEntity.ok(pointTransactionService.getAllPointTransactions());
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PointTransaction> getPointTransactionById(@PathVariable Long id) {
		return pointTransactionService.getPointTransactionById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<PointTransaction> createPointTransaction(
			@RequestBody PointTransactionDto pointTransactionDto) {
		PointTransaction newPointTransaction = pointTransactionService.createPointTransaction(pointTransactionDto);
		return ResponseEntity.ok(newPointTransaction);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<PointTransaction> updatePointTransaction(@PathVariable Long id,
			@RequestBody PointTransactionDto updatedPointTransactionDto) {
		try {
			PointTransaction updatedTransaction = pointTransactionService.updatePointTransaction(id,
					updatedPointTransactionDto);
			return ResponseEntity.ok(updatedTransaction);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePointTransaction(@PathVariable Long id) {
		pointTransactionService.deletePointTransaction(id);
		return ResponseEntity.noContent().build();
	}

}
