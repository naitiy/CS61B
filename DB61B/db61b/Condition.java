package db61b;

import java.util.List;

/** Represents a single 'where' condition in a 'select' command.
 *  @author Michael Ferrin*/
class Condition {

    /** A Condition representing COL1 RELATION COL2, where COL1 and COL2
     *  are column designators. and RELATION is one of the
     *  strings "<", ">", "<=", ">=", "=", or "!=". */
    Condition(Column col1, String relation, Column col2) {
        _col1 = col1;
        _col2 = col2;
        _relation = relation;
        _lightSwitch = true;
    }

    /** A Condition representing COL1 RELATION 'VAL2', where COL1 is
     *  a column designator, VAL2 is a literal value (without the
     *  quotes), and RELATION is one of the strings "<", ">", "<=",
     *  ">=", "=", or "!=".
     */
    Condition(Column col1, String relation, String val2) {
        this(col1, relation, (Column) null);
        _val2 = val2;
        _lightSwitch = false;
    }

    /** Assuming that ROWS are rows from the respective tables from which
     *  my columns are selected, returns the result of performing the test I
     *  denote. */
    boolean test(Row... rows) {
        String c1, c2;
        if (_lightSwitch) {
            c1 = _col1.getFrom(rows);
            c2 = _col2.getFrom(rows);
        } else {
            c1 = _col1.getFrom(rows);
            c2 = _val2;
        }
        if (_relation.equals("<")) {
            if (c1.compareTo(c2) < 0) {
                return true;
            }
        }
        if (_relation.equals(">")) {
            if (c1.compareTo(c2) > 0) {
                return true;
            }
        }
        if (_relation.equals("<=")) {
            if (c1.compareTo(c2) <= 0) {
                return true;
            }
        }
        if (_relation.equals(">=")) {
            if (c1.compareTo(c2) >= 0) {
                return true;
            }
        }
        if (_relation.equals("=")) {
            if (c1.compareTo(c2) == 0) {
                return true;
            }
        }
        if (_relation.equals("!=")) {
            if (c1.compareTo(c2) != 0) {
                return true;
            }
        }
        return false;
    }

    /** Return true iff ROWS satisfies all CONDITIONS. */
    static boolean test(List<Condition> conditions, Row... rows) {
        for (Condition cond : conditions) {
            if (!cond.test(rows)) {
                return false;
            }
        }
        return true;
    }

    /** The operands of this condition.  _col2 is null if the second operand
     *  is a literal. */
    private Column _col1, _col2;
    /** Second operand, if literal (otherwise null). */
    private String _val2;
    /** Relationship between the two. */
    private String _relation;
    /** Boolean for which constructor is called. */
    private Boolean _lightSwitch;
}
