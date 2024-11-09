package com.ibrahima.sudokusolver;

public class EasyRuleStrategy implements RuleApplicationStrategy{
    private DeductionRule rule1 = DeductionRuleFactory.createRule("DR1");

    @Override
    public boolean applyRules(SudokuGrid grid) {
        return rule1.apply(grid);
    }
}
