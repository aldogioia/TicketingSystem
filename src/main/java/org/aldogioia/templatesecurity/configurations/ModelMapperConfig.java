package org.aldogioia.templatesecurity.configurations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.CustomerCreateDto;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.creates.ValidatorCreateDto;
import org.aldogioia.templatesecurity.data.entities.Customer;
import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.aldogioia.templatesecurity.data.entities.Validator;
import org.aldogioia.templatesecurity.data.enumerators.Role;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * Configurazione per il bean {@link ModelMapper}.
 * <p>
 * Definisce e configura un'istanza di {@link ModelMapper} per la mappatura automatica tra oggetti.
 * Abilita il matching dei campi e imposta il livello di accesso ai campi pubblici.
 */
@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> passwordConverter = context -> passwordEncoder.encode(context.getSource());

        //mappatura per la creazione di un customer
        modelMapper.addMappings(new PropertyMap<CustomerCreateDto, Customer>() {
            @Override
            protected void configure() {
                map().setRole(Role.ROLE_CUSTOMER);
                using(passwordConverter).map(source.getPassword(), destination.getPassword());
            }
        });

        //mappatura per la creazione di un validator
        modelMapper.addMappings(new PropertyMap<ValidatorCreateDto, Validator>() {
            @Override
            protected void configure() {
                map().setRole(Role.ROLE_VALIDATOR);
                using(passwordConverter).map(source.getPassword(), destination.getPassword());
            }
        });

        //mappatura per la creazione del prezzo di una mostra
        modelMapper.addMappings(new PropertyMap<ExhibitionPriceCreateDto, ExhibitionPrice>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getExhibition());
                skip(destination.getTicketType());
            }
        });

        //mappatura per la creazione di un ticket
        modelMapper.addMappings(new PropertyMap<TicketCreateDto, Ticket>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getExhibitionPrice());
                skip(destination.getUser());

                using(ctx -> ctx.getSource() == null ? 1 : ctx.getSource())
                        .map(source.getPeopleNumber(), destination.getPeopleNumber());

                map().setIssuedOn(LocalDateTime.now());
                map().setStatus(TicketStatus.VALID);
            }
        });


        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}
