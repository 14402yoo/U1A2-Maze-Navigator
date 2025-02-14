import becker.robots.*;

public class RobotTask {

	public void run() {
		// build a random city called waterloo
		City maze = new City("A2Maze.txt");
		maze.showThingCounts(true);
		// build a robot in the city waterloo starting at street 4, avenue 1
		RobotAdvanced r = new RobotAdvanced(maze, 4, 4, Direction.NORTH, 0);
		r.repeat(() -> {
			r.pickThing();
		}, 4);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Left, 2);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 1);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Left, 1);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 3);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 8);
		r.repeat(() -> {
			r.pickThing();
		}, 4);
		r.undo_actions(1);
		r.move_tracked(1);
		r.clear_action_logs();

		r.turn_and_move_tracked(RobotAdvanced.Turn.Left, 2);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 1);
		r.repeat(() -> {
			r.pickThing();
		}, 4);
		r.turnBack();
		r.undo_actions(1);
		r.turnBack();
		r.repeat(() -> {
			r.moveDR_tracked();	
			r.turn_left_tracked();
		}, 3);

		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 1);	
		r.turn_and_move_tracked(RobotAdvanced.Turn.Left, 3);	
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 3);	
		r.pickThing();
		r.turnBack();
		r.undo_all_actions();
		r.clear_action_logs();

		r.turnBack();
		r.moveDR_tracked();
		r.turn_and_move_tracked(RobotAdvanced.Turn.Left, 4);	
		r.turn_left_tracked();
		r.moveDR_tracked();
		r.repeat(() -> {
			r.pickThing();
		}, 2);
		r.turnBack();
		r.undo_actions(1);
		r.turn_and_move_tracked(RobotAdvanced.Turn.Right, 4);
		r.turn_right_tracked();
		r.repeat(() -> {
			r.moveDL_tracked();
			r.turn_right_tracked();
		}, 2);
		r.move_tracked(1);
		r.repeat(() -> {
			r.pickThing();
		}, 5);
		r.turnBack();	
		r.print_number_of_actions_remained();	
		r.undo_actions(10);
		r.clear_action_logs();

		//returns to home
		r.move(5);
		r.turn_and_move(RobotAdvanced.Turn.Right, 4);
		r.turnRight();
		r.moveDR();
		r.turnLeft();
		r.moveDR();
		r.turn_and_move(RobotAdvanced.Turn.Left, 2);
	}
}
