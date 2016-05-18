package battleships.server;

public class BattleOverseer extends Thread {
	private Game myGame;
	private int remainingTime;
	private WFPState wfp=new WFPState(myGame);
	private DeployState deploy=new DeployState(myGame);
	private RoundState round=new RoundState(myGame);
	private UpdateState update=new UpdateState(myGame);
	
	public BattleOverseer(Game game) {
		myGame=game;
	}
	@Override
	public void run() {
		
		try{
			while(!interrupted()){
				myGame.changeState(wfp);
			
				myGame.deployShipsInformation();
				sleep(1000);
				myGame.resendDeployShipsInformation();
				sleep(1000);
				myGame.resendDeployShipsInformation();
				myGame.deleteNotConfirmedPl();
				
				myGame.changeState(deploy);
				for (int i=myGame.getDeployTime()/1000;i>=0;i--){
					remainingTime=i;
					sleep(1000);
				}
				
				while(true){
					myGame.changeState(round);
					sleep(60000);
					myGame.changeState(update);
				}
			}
		}catch(InterruptedException e){}
	}
	
	public int getRemainingTime(){
		return remainingTime;
	}
}
