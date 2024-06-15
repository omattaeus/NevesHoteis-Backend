package br.com.nevesHoteis.domain.validation.People;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ValidateCpfPeople implements ValidatePeople{

    @Override
    public void validate(People people) {
        String cpf=  people.getCpf().replace(".","").replace("-", "");
        if(!(verificateTenthDigit(cpf) && verificateEleventhDigit(cpf) )){
            throw new ValidateUserException("CPF", "Cpf inválido");
        }
    }

    private boolean verificateEleventhDigit(String cpf) {
        int sum=0;
        String[] cpfArray = cpf.split("");
        for (int index=11; index>1; index--){
            sum+= Integer.parseInt(cpfArray[11-index])*index;
        }
        int restResult = sum%11;
        int thenDigit = Integer.parseInt( cpfArray[10]);
        return (restResult<2 && thenDigit==0) || (restResult>=2 && thenDigit== (11-restResult));
    }

    private boolean verificateTenthDigit(String cpf){
      int sum=0;
      String[] cpfArray = cpf.split("");
      for (int index=10; index>1; index--){
          sum+= Integer.parseInt(cpfArray[10-index])*index;
      }
      int restResult = sum%11;
      int thenDigit = Integer.parseInt( cpfArray[9]);
      return (restResult<2 && thenDigit==0) || (restResult>=2 && thenDigit== (11-restResult));
    }


}
