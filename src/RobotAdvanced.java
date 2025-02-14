import becker.robots.*;
import java.util.Stack;

public class RobotAdvanced extends Robot{
    /**
     * Enum type of turning directions.
     * Default value is null.
     */
    public static enum Turn {
        Back("back"),
        Left("left"),
        Right("right");
        public final String dir;
        /**
         * 
         * @param dir turing direction
         */
        private Turn(String dir) {
            this.dir = dir;
        }
    }
    
    /**
     * Action class for defining each turhing, moving, or either. 
     * Using this class, action logs tracking is convineient and can define turn and move as one action.
     */
	public static class Action {
		public Turn turn;
		public int move = 0;
        /**
         * 
         * @param turn Direction of turning
         * @param move Blocks to move forward
         */
		public Action(Turn turn, int move) {
			this.turn = turn;
			this.move = move;
		}
        /**
         * 
         * @param turn Direction of turning
         */
        public Action(Turn turn) {
            this.turn = turn;
        }
        /**
         * 
         * @param move Blocks to move forward
         */
		public Action(int move) {
			this.move = move;
		}
	}
    
        /**
         * A stack to record actions.
         * Can push and pop recent actions.
         */
        public final Stack<Action> actionLogs = new Stack<>();
    
        /**
         * 
         * @param var1 City
         * @param var2 X coordinate of starting point
         * @param var3 Y coordinate of starting point
         * @param var4 Initial Direction of robot heading
         * @param var5 Initial number of Things robot holds
         */
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
    
        /**
         * Turns robot left and adds it to the action logs.
         */
        public void turn_left_tracked() {
            turnLeft();
            System.out.println("turn left");
            actionLogs.add(new Action(Turn.Left));
        }
    
        /**
         * Turns robot right by repeating turnLeft() 3 times.
         * Not recorded in the action logs. 
         */
        //TODO: should i override turnRight??
        public void turnRight() {
            repeat(() -> turnLeft(), 3);
            System.out.println("turn right");
        }
        
        /**
         * Turns robot right and adds it to the action logs.
         */
        public void turn_right_tracked() {
            turnRight();        
            actionLogs.push(new Action(Turn.Right));
        }
    
        /**
         * turns robot back by repeating turnLeft 2 times.
         * Not recorded in the action logs.
         */
        public void turnBack() {
            System.out.println("turn back");
            repeat(() -> turnLeft(), 2);
        }
        
        /**
         * Turns robot back and adds it to the action logs.
         */
        public void turn_back_tracked() {
            turnBack();
            actionLogs.push(new Action(Turn.Back));
        }
    
        /**
         * Performs turning first and then moves robot forward by n blocks.
         * Not recorded in the action logs.
         * @param dir Direction to turn
         * @param n The number of blocks to move forward
         */
        public void turn_and_move(Turn dir, int n) {
            turn(dir);
            move(n);
        }
       
        /**
         * Performs turning first and then moves robot forward by n blocks.
         * Turning and moving is recorded as one action to the action logs. 
         * @param dir direction to turn (Left, Right, Backward)
         * @param n the number of blocks to move after turning
         */
        public void turn_and_move_tracked(Turn dir, int n) {
           turn(dir); 
           move(n);
           actionLogs.add(new Action(dir, n));
        }
        
        /**
         * Performs turning based on the specified turning direction.
         * Not recorded in the action logs.
         * @param turn Direction to turn
         */
        public void turn(Turn turn) {
            switch (turn) {
                case Back:  
                    turnBack();
                    break;
                case Left:
                    this.turnLeft();
                    break;
                case Right: 
                    turnRight();
                    break;
                default: break;
            }
        }
        
        /**
         * Performs turning based on the specified turning direction.
         * Recorded as a new action to the action logs. 
         * @param turn Direction to turn
         */
        public void turn_tracked(Turn turn) {
            switch (turn) {
                case Back: 
                    turn_back_tracked();
                    break;
                case Left: 
                    turn_left_tracked(); 
                    break;
                case Right: 
                    turn_right_tracked();
                    break;
                default: break;
            }
        }
    
        /**
         * Moves robot for certain amounts of time if there is no wall in its front. 
         * Not recorded in the action logs.
         * @param n the number of times for robot to move forward by one.
         */
        public void move(int n) {
            repeat(() -> {
                move(); 
            }, n);
        }
    
        /**
         * Moves robot for certain amounts of time if there is no wall in its front. 
         * Recorded as a new action to the action logs.
         * @param n the number of times for robot to move forward by one.
         */
        public void move_tracked(int n) {
            move(n);
            actionLogs.add(new Action(n));
        }
    
        /**
         * Moves robot 1 diagonal intersection to the right (up one then right one.
         * Not recorded in the action logs.
         */
        public void moveDR() {
            move(1);
            turnRight();
            move(1);
        }
    
        /**
         * Moves robot 1 diagonal intersection to the right (up one then right one).
         * Recorded as 3 new actions in the action logs in order of move, turn left, and move.
         */
        public void moveDR_tracked() {
            move_tracked(1);
            turn_right_tracked();
            move_tracked(1);
        }
    
        /**
         * Moves robot 1 diagonal intersection to the right (up one then left one)
         * Not recorded in the action logs.
         */
        public void moveDL() {
            move(1);
            turnLeft();
            move(1);
        }
    
        /**
         * Moves robot 1 diagonal intersection to the right (up one then left one)
         * Recorded as 3 new actions in the action logs in order of move, turn left, and move. 
         */
        public void moveDL_tracked() {
            move_tracked(1);
            turn_left_tracked();
            move_tracked(1);
        }
    
        /**
         * Pick a thing if there is one beside in any directions.
         * Not recorded in the action logs.
         */
        @Override
        public void pickThing() {
            if (isBesideThing(IPredicate.aThing)) {
                System.out.println("Picked a Thing");
                super.pickThing();
            }
        }
    
        /**
         * Performs reverse action of the lastest action in the action logs.
         * Once the reverse action is performed, the latest action is removed from the log.
         * The reverse action is not recorded in the action logs again.
         */
        public void perform_reversed_action() {
            if (actionLogs.empty()) {
                System.out.println("Robot Action logs is empty");
                return;
            }
            Action action = actionLogs.lastElement();
            actionLogs.pop();
            if (action.move > 0) {
                if (!frontIsClear()) {
                    turnBack();
                }
                move(action.move);
            }
            if (action.turn != null) {
                switch (action.turn) {
                case Back: 
                    turnBack();
                    break;
                case Left: 
                    turnRight();
                    break;
                case Right: 
                    turnLeft();
                    break;
                default: break;
            }
        }
    }

    /**
     * Performs reverse actions of the actions tracked in the robot action logs up to n times, removing each action from the action logs stack.
     * If the value of n is greater than the number of actions currently in the action logs, noting will happen but a notification.
     * @param n the number of actions to be undone
     */
    public void undo_actions(int n) {
        System.out.println("undo action");
        repeat(() -> {
            perform_reversed_action(); 
        }, n);
    } 

    /**
     * Performs all reverse actions of the actions tracked in the robot action logs until the action logs stack is empty.
     */
    public void undo_all_actions() {
        while (!actionLogs.empty()) {
            perform_reversed_action();
        }
    }

    /**
     * Prints the current number of actions in the action logs stack.
     */
    public void print_number_of_actions_remained() {
        System.out.println(actionLogs.size() + " actions remained in logs");
    }

    /**
     * Removes all actions in the action logs stack.
     */
    public void clear_action_logs() {
        actionLogs.clear();
    }
}
