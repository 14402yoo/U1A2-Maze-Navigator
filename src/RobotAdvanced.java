import becker.robots.*;
import java.util.Stack;

public class RobotAdvanced extends Robot{
    public static enum Turn {
        Back("back"),
        Left("left"),
        Right("right");
        public final String dir;
        private Turn(String dir) {
            this.dir = dir;
        }
    }
	
	public static class Action {
		public Turn turn;
		public int move = 0;
		public Action(Turn turn, int move) {
			this.turn = turn;
			this.move = move;
		}
        public Action(Turn turn) {
            this.turn = turn;
        }
		public Action(int move) {
			this.move = move;
		}
	}

    public final Stack<Action> actionLogs = new Stack<Action>();

    public RobotAdvanced(City var1, int var2, int var3, Direction var4, int var5) {
        super(var1, var2, var3, var4, var5);
    } 

    /**
     * repeates a runnable function for n times 
     * @param func the function you want to run 
     * @param n the number of times the function is repeated
     */
    public void repeat(Runnable func, int n) {
        for (int i = 0; i < n; ++i) {
            func.run();
        }
    }

    public void turn_left_tracked() {
        turnLeft();
        System.out.println("turn left");
        actionLogs.add(new Action(Turn.Left));
    }

    /**
     * turns robot right by repeating turnLeft() 3 times 
     */
    //TODO: should i override turnRight??
    public void turnRight() {
        repeat(() -> turnLeft(), 3);
        System.out.println("turn right");
    }
    
    public void turn_right_tracked() {
        turnRight();        
        actionLogs.push(new Action(Turn.Right));
    }

    /**
     * turns robot back by repeating turnLeft 2 times
     */
    public void turnBack() {
        System.out.println("turn back");
        repeat(() -> turnLeft(), 2);
    }
    
    public void turn_back_tracked() {
        turnBack();
        actionLogs.push(new Action(Turn.Back));
    }

    public void turn_and_move(Turn dir, int n) {
        turn(dir);
        move(n);
    }
   
    /**
     * turns first and then moves forwards in specified amounts of blocks. 
     * @param dir direction to turn (Left, Right, Backward)
     * @param n the number of blocks to move after turning
     */
    public void turn_and_move_tracked(Turn dir, int n) {
       turn(dir); 
       move(n);
       actionLogs.add(new Action(dir, n));
    }
    
    public void turn(Turn turn) {
        switch (turn) {
            case Turn.Back -> turnBack();
            case Turn.Left -> turnLeft(); 
            case Turn.Right -> turnRight();
        }
    }
    
    public void turn_tracked(Turn turn) {
        switch (turn) {
            case Turn.Back -> turn_back_tracked();
            case Turn.Left -> turn_left_tracked(); 
            case Turn.Right -> turn_right_tracked();
        }
    }

    /**
     * moves robot for certain amounts of time if there is no wall in its front. 
     * @param n the number of times for robot to move forward by one.
     */
    public void move(int n) {
        repeat(() -> {
            //if (frontIsClear()) {
               move(); 
            //}
        }, n);
    }

    public void move_tracked(int n) {
        move(n);
        actionLogs.add(new Action(n));
    }

    /**
     * moves robot 1 diagonal intersection to the right (up one then right one)
     */
    public void moveDR() {
        move(1);
        turnRight();
        move(1);
    }


    public void moveDR_tracked() {
        move_tracked(1);
        turn_right_tracked();
        move_tracked(1);
    }

    /**
     * moves robot 1 diagonal intersection to the right (up one then left one)
     */
    public void moveDL() {
        move(1);
        turnLeft();
        move(1);
    }

    public void moveDL_tracked() {
        move_tracked(1);
        turn_left_tracked();
        move_tracked(1);
    }

    /**
     * pick a thing if there is one beside in any directions
     */
    @Override
    public void pickThing() {
        if (isBesideThing(IPredicate.aThing)) {
            System.out.println("Picked a Thing");
            super.pickThing();
        }
    }

    public void perform_reversed_action() {
        if (actionLogs.empty()) {
            System.out.println("Robot Action logs is empty");
            return;
        }
        var action = actionLogs.getLast();
        actionLogs.pop();
        if (action.move > 0) {
            if (!frontIsClear()) {
                turnBack();
            }
            move(action.move);
        }
        if (action.turn != null) {
            switch (action.turn) {
                case Turn.Back -> turnBack();
                case Turn.Left -> turnRight(); 
                case Turn.Right -> turnLeft();
            }
        }
    }

    /**
     *  performs reversed actions of the actions tracked in the robot action logs 
     * @param n the number of actions to be undone
     */
    public void undo_actions(int n) {
        System.out.println("undo action");
        repeat(() -> {
            perform_reversed_action(); 
        }, n);
    } 

    public void undo_all_actions() {
        while (!actionLogs.empty()) {
            perform_reversed_action();
        }
    }

    public void print_number_of_actions_remained() {
        System.out.println(actionLogs.size() + " actions remained in logs");
    }

    public void clear_action_logs() {
        actionLogs.clear();
    }
}
