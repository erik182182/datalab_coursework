@echo Exported database
@set /P host1="Host:"
@set /P username1="Username:"
@set /P dbname1="DB Name:"
@pg_dump -Fc -v --host=%host1% --username=%username1% --dbname=%dbname1% -f C:\Users\erik1\1Ernest\Programming\Projects\IdeaProjects\ObfDataCoursework\src\main\tmp\%host1%-%dbname1%.dump

@if %ERRORLEVEL% == 1 exit /b 1
@echo.
@echo New database
@set /P host2="Host:"
@set /P username2="Username:"
@set /P dbname2="DB Name:"
@psql -U %username2% -h %host2% -c "create database \"%dbname2%\";"
@pg_restore -v --no-owner --host=%host2% --port=5432 --username=%username2% --dbname=%dbname2% C:\Users\erik1\1Ernest\Programming\Projects\IdeaProjects\ObfDataCoursework\src\main\tmp\%host1%-%dbname1%.dump

@echo.
@echo The process has ended, the console can be closed.