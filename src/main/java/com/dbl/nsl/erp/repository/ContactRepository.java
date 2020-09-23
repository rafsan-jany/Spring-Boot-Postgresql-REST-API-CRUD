package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbl.nsl.erp.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
