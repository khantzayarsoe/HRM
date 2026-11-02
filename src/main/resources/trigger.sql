delimiter //
create trigger check_empID
    after INSERT on employees
    for each row
begin
    INSERT INTO emp_id_log (empID) VALUES (NEW.empid);
end; //
