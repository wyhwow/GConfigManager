grammar TypeDef;

type_defs: type_def *;

type_def: TYPE ID LB attrs RB;

attrs: attr*;

attr: type ID;

type: DOUBLE | INT | BOOL | STRING ;

DOUBLE:'double';
INT:'int';
BOOL:'bool';
STRING:'string';

LB:'{';
RB:'}';

TYPE:'type';

ID:[a-zA-X_][a-zA-X0-9_]*;

WS : [ \t\r\n]+ -> skip ;

