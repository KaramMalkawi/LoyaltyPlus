package com.example.loyaltyplus.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.dto.PointTransactionDto;
import com.example.loyaltyplus.model.entity.PointTransaction;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.PointTransactionRepository;
import com.example.loyaltyplus.repository.UserRepository;

@Service
public class PointTransactionService {
	@Autowired
	private PointTransactionRepository pointTransactionRepository;

	@Autowired
	private final UserRepository userRepository;

	public PointTransactionService(PointTransactionRepository pointTransactionRepository,
			UserRepository userRepository) {
		this.userRepository = userRepository;
		this.pointTransactionRepository = pointTransactionRepository;
	}

	public List<PointTransaction> getAllPointTransactions() {
		return pointTransactionRepository.findAll();
	}

	public Optional<PointTransaction> getPointTransactionById(Long id) {
		return pointTransactionRepository.findById(id);
	}

	public PointTransaction createPointTransaction(PointTransactionDto pointTransactionDto) {
		PointTransaction newPointTransaction = new PointTransaction();

		// Set the properties of the new point transaction
		newPointTransaction.setPointsChanged(pointTransactionDto.getPointsChanged());
		newPointTransaction.setDescription(pointTransactionDto.getDescription());

		Long userId = pointTransactionDto.getUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found"));
		newPointTransaction.setUser(user);

		newPointTransaction.setCreatedAt(new Date());

		// Save the new point transaction to the database
		return pointTransactionRepository.save(newPointTransaction);
	}

	public PointTransaction updatePointTransaction(Long id, PointTransactionDto updatedPointTransactionDto) {
		Optional<PointTransaction> existingPointTransactionOptional = pointTransactionRepository.findById(id);

		if (existingPointTransactionOptional.isPresent()) {
			PointTransaction existingPointTransaction = existingPointTransactionOptional.get();

			if (updatedPointTransactionDto.getPointsChanged() != 0)
				existingPointTransaction.setPointsChanged(updatedPointTransactionDto.getPointsChanged());
			if (updatedPointTransactionDto.getDescription() != null)
				existingPointTransaction.setDescription(updatedPointTransactionDto.getDescription());
			if (updatedPointTransactionDto.getUserId() != null) {
				Long userId = updatedPointTransactionDto.getUserId();
				User user = userRepository.findById(userId)
						.orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " was not found"));
				existingPointTransaction.setUser(user);
			}

			existingPointTransaction.setUpdatedAt(new Date());
			return pointTransactionRepository.save(existingPointTransaction);
		} else {
			throw new IllegalArgumentException("PointTransaction with id " + id + " was not found");
		}
	}

	public void deletePointTransaction(Long id) {
		pointTransactionRepository.deleteById(id);
	}
}
