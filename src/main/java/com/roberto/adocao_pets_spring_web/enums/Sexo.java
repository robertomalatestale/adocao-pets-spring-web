package com.roberto.adocao_pets_spring_web.enums;

import com.roberto.adocao_pets_spring_web.exceptions.InvalidInputException;

public enum Sexo {
        MACHO(new String[]{"m","masculino","macho","male"}),
        FEMEA(new String[]{"f","feminino","femea","fêmea","female"});

        private final String[] aliases;

        Sexo(String[] aliases){
            this.aliases = aliases;
        }

        public static Sexo identifyGender(String userInput){
            for (Sexo sexo : Sexo.values()){
                for (String alias : sexo.aliases){
                    if(alias.equalsIgnoreCase(userInput.trim())){
                        return sexo;
                    }
                }
            }
            throw new InvalidInputException("Escolha entre masculino e feminino");
        }
}
