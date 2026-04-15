package com.codurance.srp;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toCollection;

public class StatementPrinter {
    private static final String STATEMENT_HEADER = "DATE | AMOUNT | BALANCE";

    private TransactionRepository transactionRepository;
    private StatementFormatter formatter;
    private Console console;

    public StatementPrinter(TransactionRepository transactionRepository, StatementFormatter formatter, Console console) {
        this.transactionRepository = transactionRepository;
        this.formatter = formatter;
        this.console = console;
    }

    public void print() {
        printHeader();
        printTransactions();
    }

    private void printHeader() {
        console.printLine(STATEMENT_HEADER);
    }

    private void printTransactions() {
        List<Transaction> transactions = transactionRepository.all();
        final AtomicInteger balance = new AtomicInteger(0);
        transactions.stream()
                .map(transaction -> formatter.statementLine(transaction, balance.addAndGet(transaction.amount())))
                .collect(toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(console::printLine);
    }
}
