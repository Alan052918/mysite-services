package com.example.user_account;

import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAccountModelAssembler implements RepresentationModelAssembler<UserAccount, EntityModel<UserAccount>> {

    @Override
    @NonNull
    public EntityModel<UserAccount> toModel(@NonNull UserAccount userAccountEntity) {
        return EntityModel.of(userAccountEntity,
                linkTo(methodOn(UserAccountController.class).getUserAccountById(userAccountEntity.getId())).withSelfRel(),
                linkTo(methodOn(UserAccountController.class).getAllUserAccounts()).withRel("users"));
    }

    @Override
    @NonNull
    public CollectionModel<EntityModel<UserAccount>> toCollectionModel(@NonNull Iterable<? extends UserAccount> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
