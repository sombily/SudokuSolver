package com.ibrahima.sudokusolver;

public class MediumRuleStrategy implements RuleApplicationStrategy{
    private DeductionRule rule1 = DeductionRuleFactory.createRule("DR1");
    private DeductionRule rule2 = DeductionRuleFactory.createRule("DR2");

    @Override
    public boolean applyRules(SudokuGrid grid) {
        boolean progress = false;
        if(rule1.apply(grid)) {
            progress = true;
        }
        if(rule2.apply(grid)) {
            progress = true;
        }
        return progress;
    }
}
