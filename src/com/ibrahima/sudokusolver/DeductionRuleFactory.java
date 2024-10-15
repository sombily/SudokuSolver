package com.ibrahima.sudokusolver;
// Patron Fabrique pour la creation des regles
public class DeductionRuleFactory {
    public static DeductionRule createRule(String ruleType){
        switch (ruleType) {
            case "DR1":
                return new RuleDR1();
            case "DR2":
                return new RuleDR2();
            case "DR3":
                return new RuleDR3();
            default:
                throw new IllegalArgumentException("Regle Inconue:" + ruleType);
        }
    }
}
