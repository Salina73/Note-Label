package com.StartNotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StartNotes.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> 
{

}