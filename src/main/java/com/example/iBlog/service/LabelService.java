package com.example.iBlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iBlog.domain.Label;
import com.example.iBlog.repository.LabelRepository;

@Service
public class LabelService {

	@Autowired
	private LabelRepository labelRepository;

	public Label addLabel(Label label) {
		return labelRepository.save(label);
	}

	public List<Label> findByUserNumber(int userNumber) {
		return labelRepository.findByUserNumber(userNumber);
	}

	public Label findByLabelNameAndUserName(String userName, String labelName) {
		return labelRepository.findByLabelNameAndUserName(userName, labelName);
	}

	public List<Label> findByUserNameAndLabelState(String userName) {
		return labelRepository.findByUserNameAndLabelState(userName);
	}

	public Label findByLabelName(String labelName) {
		return labelRepository.findByLabelName(labelName);
	}

	public int UpdateState(boolean newState, int labelNumber) {
		return labelRepository.UpdateState(newState, labelNumber);
	}

	public Label getOne(int labelNumber) {
		return labelRepository.getOne(labelNumber);
	}

	public List<Label> findAll() {
		return labelRepository.findAll();
	}
	public void deleteByLabelNumber(int labelNumber) {
		labelRepository.deleteById(labelNumber);
	}
}
