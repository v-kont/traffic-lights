# traffic-lights
Small traffic lights project

Task:
        Create array of arbitrary number of different traffic lights.
        Create program which will manage them: first traffic light begin with red light,
        and each next traffic light begin with opposite color (green/red/green/red/green etc) to previous one.
        For Japanese traffic light green light must be replaced for blue light due local cultural traditions
        (technically it's a green color but with max blue tint).
    
        After two minutes in one state traffic light must automatically change to opposite color.
        User also must have opportunity to manage current traffic lights. For example, due user command
        traffic light' state (run/stop, current light etc.) should be changed to opposite. Before
        change light traffic light should be set to yellow light for 10 seconds.
        
        All states should be logged to file.
        
Comments:
        1. Yes, I know AbstractFactory + Concurrency a little bit ( :) ) overwhelming
        for those simple traffic lights, but it's just a showcase (and for Open-Close principle as well);
        
        2. Internalization should be done in another way (more shortly, cause we can use just one Factory class), so
        Russian/English/Japanese traffic lights I used just for simplification of distinction
    
    
TODO:
        3. Implement correct thread synchronization
        4. move fabrics into Manager. use them by command 'add' from user
        5. Implement annotation for checking lang codes
        6. Implements all required checks
        7. Turn manager into daemon and implement Client-Server architecture
        8. Create classes for unit testing and test it
