package com.ibrahima.sudokusolver;

public class DifficultRuleStrategy implements RuleApplicationStrategy{
    private DeductionRule rule1 = DeductionRuleFactory.createRule("DR1");
    private DeductionRule rule2 = DeductionRuleFactory.createRule("DR2");
    private DeductionRule rule3 = DeductionRuleFactory.createRule("DR3");

    @Override
    public boolean applyRules(SudokuGrid grid) {
        boolean progress = false;
        if(rule1.apply(grid)) progress = true;
        if(rule2.apply(grid)) progress = true;
        if(rule3.apply(grid)) progress = true;
        return progress;
    }
}
