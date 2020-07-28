package com.kodilla.walletmanager.domain.dto;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private double amount;
    private TransactionType type;
    private Date date;
    private CategoryDto categoryDto;

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        if (ToolsManager.isTheSameEnum(categoryDto.getType(),type)){
            this.categoryDto = categoryDto;
        }else {
            System.out.println("Not the came type TransactionType");
        }
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", categoryDto=" + categoryDto +
                '}';
    }

    public static class TransactionDtoBuilder {
        private Long id;
        private Date date =  new Date(new java.util.Date().getTime());
        private TransactionType type;
        private String title;
        private String description;
        private double amount;
        private CategoryDto category;

        public TransactionDtoBuilder id(Long id){
            this.id = id;
            return this;
        }

        public TransactionDtoBuilder date(Date date){
            this.date = date;
            return this;
        }

        public TransactionDtoBuilder type(TransactionType type){
            this.type = type;
            return this;
        }

        public TransactionDtoBuilder title(String title){
            this.title = title;
            return this;
        }

        public TransactionDtoBuilder description(String description){
            this.description = description;
            return this;
        }

        public TransactionDtoBuilder amount(double amount){
            this.amount = amount;
            return this;
        }

        public TransactionDtoBuilder category(CategoryDto category){
            this.category = category;
            return this;
        }

        public TransactionDto build(){
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(id);
            transactionDto.setDate(date);
            transactionDto.setType(type);
            transactionDto.setTitle(title);
            transactionDto.setDescription(description);
            transactionDto.setAmount(amount);
            transactionDto.setCategoryDto(category);

            return transactionDto;
        }
    }
}
