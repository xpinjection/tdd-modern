// ============================================================================
//  File          : Book
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Book {
    @Id @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String author;
}
