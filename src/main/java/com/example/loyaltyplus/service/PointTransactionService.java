package com.example.loyaltyplus.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loyaltyplus.model.dto.PointTransactionDto;
import com.example.loyaltyplus.model.entity.PointTransaction;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.PointTransactionRepository;
import com.example.loyaltyplus.repository.UserRepository;

@Service
@Transactional
public class PointTransactionService {

	private final PointTransactionRepository pointTransactionRepository;
	private final UserRepository userRepository;

	public PointTransactionService(PointTransactionRepository pointTransactionRepository,
			UserRepository userRepository) {
		this.pointTransactionRepository = pointTransactionRepository;
		this.userRepository = userRepository;
	}

	public List<PointTransaction> getAllPointTransactions() {
		return pointTransactionRepository.findAll();
	}

	public Optional<PointTransaction> getPointTransactionById(Long id) {
		return pointTransactionRepository.findById(id);
	}

	public PointTransaction createPointTransaction(PointTransactionDto pointTransactionDto) {
		PointTransaction newPointTransaction = new PointTransaction();
		newPointTransaction.setPointsChanged(pointTransactionDto.getPointsChanged());
		newPointTransaction.setDescription(pointTransactionDto.getDescription());
		newPointTransaction.setCreatedAt(new Date());

		User user = userRepository.findById(pointTransactionDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		newPointTransaction.setUser(user);

		return pointTransactionRepository.save(newPointTransaction);
	}

	public PointTransaction updatePointTransaction(Long id, PointTransactionDto updatedPointTransactionDto) {
		PointTransaction existingPointTransaction = pointTransactionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("PointTransaction not found"));

		if (updatedPointTransactionDto.getPointsChanged() != 0) {
			existingPointTransaction.setPointsChanged(updatedPointTransactionDto.getPointsChanged());
		}
		if (updatedPointTransactionDto.getDescription() != null) {
			existingPointTransaction.setDescription(updatedPointTransactionDto.getDescription());
		}
		if (updatedPointTransactionDto.getUserId() != null) {
			User user = userRepository.findById(updatedPointTransactionDto.getUserId())
					.orElseThrow(() -> new IllegalArgumentException("User not found"));
			existingPointTransaction.setUser(user);
		}

		existingPointTransaction.setUpdatedAt(new Date());
		return pointTransactionRepository.save(existingPointTransaction);
	}

	public void deletePointTransaction(Long id) {
		if (!pointTransactionRepository.existsById(id)) {
			throw new IllegalArgumentException("PointTransaction not found");
		}
		pointTransactionRepository.deleteById(id);
	}
}