package com.tek.dforge.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.tek.dforge.bot.lib.commands.ICommandHandler;
import com.tek.dforge.bot.lib.util.JDAUtil;
import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.plugin.BakedPlugin;
import com.tek.dforge.ui.popup.AlertBox;
import com.tek.dforge.util.Reference;
import com.tek.dforge.util.StringUtil;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

public class MainController {
	
	private static MainController instance;
	
	public final int hamburgerWidth = 108;
	
	public String currentMenu;
	
	public ArrayList<Node> txtNodes;
	public HashMap<JFXButton, Pane> panes;
	public ArrayList<JFXButton> btnHamburgers;
	public ArrayList<Timeline> btnAnimations;
	public ArrayList<Timeline> btnAnimationsR;
	public boolean btnAnimationState;
	
	public MainController() {
		this.txtNodes = new ArrayList<Node>();
		this.btnHamburgers = new ArrayList<JFXButton>();
		this.btnAnimations = new ArrayList<Timeline>();
		this.btnAnimationsR = new ArrayList<Timeline>();
		this.panes = new HashMap<JFXButton, Pane>();
		this.btnAnimationState = false;
		this.currentMenu = "btnBotMenu";
	}
	
	@FXML
	private BorderPane paneBorder;
	
	@FXML
	private JFXHamburger hamburger;
	
	@FXML
	private JFXButton btnBotMenu;
	
	@FXML
	private JFXButton btnPlugins;
	
	@FXML
	private JFXButton btnCommands;
	
	@FXML
	private JFXButton btnConfig;
	
	@FXML
	private JFXButton btnSettings;
	
	@FXML
	private JFXButton btnInfo;
	
    @FXML
    private Pane paneBotMenu;
	
    @FXML
    private Pane panePlugins;
    
    @FXML
    private Pane paneCommands;
    
	@FXML
    private Pane paneConfig;
	
	@FXML
    private Pane paneInfo;
	
	@FXML
    private Pane paneSettings;
	
	@FXML
	private Pane paneTitle;
	
	@FXML
	private Pane paneHamburger;
	
	@FXML
    private Label lblTitle;
	
	@FXML
    private JFXColorPicker colorPrimary;

    @FXML
    private JFXColorPicker colorSecondary;

    @FXML
    private JFXColorPicker colorTertiary;
    
    @FXML
    private JFXPasswordField fieldToken;
    
    @FXML
    public JFXButton btnStart;
    
    @FXML
    public JFXButton btnStop;
    
    @FXML
    private JFXTextField fieldPresence;
    
    @FXML
    private JFXButton btnPresence;
    
    @FXML
    public ImageView imageDiscord;
    
    @FXML
    public Label lblName;
    
    @FXML
    public JFXButton btnReload;
    
    @FXML
    public JFXListView<BakedPlugin> listPlugins;
    
    @FXML
    public JFXListView<ICommandHandler> listCommands;
    
    @FXML
    public Label lblInfo;
    
    @FXML
    public JFXToggleButton toggleEnabled;
    
    @FXML
    public JFXToggleButton toggleCommandEnabled;
    
    @FXML
    public JFXListView<BakedPlugin> listPlugins1;
    
    @FXML
    public ScrollPane paneScrollConfig;
    
    @FXML
    public AnchorPane po;
    
    @FXML
    public VBox paneOptions;
    
    @FXML
    public ScrollPane paneScrollAction;
    
    @FXML
    public AnchorPane pa;
    
    @FXML
    public VBox paneActions;
    
    @FXML
    public JFXTextField fieldPrefix;
    
    @FXML
    public Label lblCommandInfo;
    
	@FXML
	private void initialize() {
		instance = this;
		
		imageDiscord.setImage(new Image(Reference.DISCORDPICTURE));
		Rectangle rectangle = new Rectangle(imageDiscord.getFitWidth(), imageDiscord.getFitHeight());
		rectangle.setArcWidth(imageDiscord.getFitWidth() / 2);
		rectangle.setArcHeight(imageDiscord.getFitHeight() / 2);
		imageDiscord.setClip(rectangle);
		
		btnStop.getStyleClass().add("disabled");
		
		btnStart.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			if(btnStart.getStyleClass().contains("disabled")) return;
			
			boolean valid = JDAUtil.verifyToken(fieldToken.getText());
			
			if(valid) {
				DiscordForge.getInstance().getDiscordBot().start();
				btnStart.getStyleClass().add("disabled");
			}else {
				AlertBox.display("Error", "Invalid Token");
			}
		});
		
		btnStop.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			if(btnStop.getStyleClass().contains("disabled")) return;
			
			DiscordForge.getInstance().getDiscordBot().stop();
			
			//For some reason this doesn't remove disabled unless I do it twice :I
			btnStart.getStyleClass().remove("disabled");
			btnStart.getStyleClass().remove("disabled");
			btnStop.getStyleClass().add("disabled");
			
			imageDiscord.setImage(new Image(Reference.DISCORDPICTURE));
			lblName.setText("NA");
		});
		
		btnPresence.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			DiscordForge.getInstance().getDiscordBot().updatePresence(DiscordForge.getInstance().getConfig().getProperty("presence"));
		});
		
		btnReload.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			DiscordForge.getInstance().getPluginManager().loadAll();
			DiscordForge.getInstance().getPluginManager().enableAll();
			
			listPlugins.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getPluginManager().getPlugins()));
			listPlugins1.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getPluginManager().getPlugins()));
			listCommands.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getDiscordBot().getCommandManager().getCommandHandlers().keySet()));
		});
		
		listPlugins.setCellFactory(new Callback<ListView<BakedPlugin>, ListCell<BakedPlugin>>(){
			@Override
			public ListCell<BakedPlugin> call(ListView<BakedPlugin> bakedPluginListView){
				return new ListCell<BakedPlugin>() {
					@Override
					protected void updateItem(BakedPlugin item, boolean empty) {
						super.updateItem(item, empty);
						
						setText(empty ? null : item.toString());
						
						if(empty) {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("tertiaryColor") + ";");
						}else {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
						}
					}
				};
			}
		});
		
		listPlugins1.setCellFactory(new Callback<ListView<BakedPlugin>, ListCell<BakedPlugin>>(){
			@Override
			public ListCell<BakedPlugin> call(ListView<BakedPlugin> bakedPluginListView){
				return new ListCell<BakedPlugin>() {
					@Override
					protected void updateItem(BakedPlugin item, boolean empty) {
						super.updateItem(item, empty);
						
						setText(empty ? null : item.toString());
						
						if(empty) {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("tertiaryColor") + ";");
						}else {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
						}
					}
				};
			}
		});
		
		listCommands.setCellFactory(new Callback<ListView<ICommandHandler>, ListCell<ICommandHandler>>(){
			@Override
			public ListCell<ICommandHandler> call(ListView<ICommandHandler> commandHandlerListView){
				return new ListCell<ICommandHandler>() {
					@Override
					protected void updateItem(ICommandHandler item, boolean empty) {
						super.updateItem(item, empty);
						
						setText(empty ? null : item.asString());
						
						if(empty) {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("tertiaryColor") + ";");
						}else {
							setStyle("-fx-control-inner-background: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
						}
					}
				};
			}
		});
		
		listPlugins.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getPluginManager().getPlugins()));
		listPlugins.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listPlugins.getSelectionModel().selectedItemProperty().addListener(e -> {
			updateInfo(listPlugins.getSelectionModel().getSelectedItem());
			updateActions(listPlugins.getSelectionModel().getSelectedItem());
		});
		
		listPlugins1.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getPluginManager().getPlugins()));
		listPlugins1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listPlugins1.getSelectionModel().selectedItemProperty().addListener(e -> {
			updateOptions(listPlugins1.getSelectionModel().getSelectedItem());
		});
		
		listCommands.setItems(FXCollections.observableArrayList(DiscordForge.getInstance().getDiscordBot().getCommandManager().getCommandHandlers().keySet()));
		listCommands.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listCommands.getSelectionModel().selectedItemProperty().addListener(e -> {
			updateCommands(listCommands.getSelectionModel().getSelectedItem());
		});
		
		fieldPresence.textProperty().addListener(e -> {
			DiscordForge.getInstance().getConfig().setProperty("presence", fieldPresence.getText());
		});
		
		fieldToken.textProperty().addListener(e -> {
			DiscordForge.getInstance().getConfig().setProperty("token", fieldToken.getText());
		});
		
		fieldPrefix.textProperty().addListener(e -> {
			DiscordForge.getInstance().getConfig().setProperty("prefix", fieldPrefix.getText());
		});
		
		toggleEnabled.selectedProperty().addListener(e -> {
			BakedPlugin plugin = listPlugins.getSelectionModel().getSelectedItem();
			
			if(plugin == null) {
				toggleEnabled.setSelected(false);
			}else {
				plugin.setEnabled(toggleEnabled.isSelected());
			}
		});
		
		toggleCommandEnabled.selectedProperty().addListener(e -> {
			ICommandHandler command = listCommands.getSelectionModel().getSelectedItem();
			
			if(command == null) {
				toggleCommandEnabled.setSelected(false);
			}else {
				command.setEnabled(toggleCommandEnabled.isSelected());
			}
		});
		
		colorPrimary.setValue(Color.valueOf(DiscordForge.getInstance().getConfig().getProperty("primaryColor")));
		colorSecondary.setValue(Color.valueOf(DiscordForge.getInstance().getConfig().getProperty("secondaryColor")));
		colorTertiary.setValue(Color.valueOf(DiscordForge.getInstance().getConfig().getProperty("tertiaryColor")));
		
		buildColorPicker(colorPrimary);
		buildColorPicker(colorSecondary);
		buildColorPicker(colorTertiary);
		
		buildHamburgerAnimations(btnBotMenu, hamburgerWidth);
		buildHamburgerAnimations(btnPlugins, hamburgerWidth);
		buildHamburgerAnimations(btnCommands, hamburgerWidth);
		buildHamburgerAnimations(btnConfig, hamburgerWidth);
		buildHamburgerAnimations(btnSettings, hamburgerWidth);
		buildHamburgerAnimations(btnInfo, hamburgerWidth);
		
		fieldToken.setText(DiscordForge.getInstance().getConfig().getProperty("token"));
		fieldPresence.setText(DiscordForge.getInstance().getConfig().getProperty("presence"));
		fieldPrefix.setText(DiscordForge.getInstance().getConfig().getProperty("prefix"));
		
		this.panes.put(btnBotMenu, paneBotMenu);
		this.panes.put(btnConfig, paneConfig);
		this.panes.put(btnPlugins, panePlugins);
		this.panes.put(btnCommands, paneCommands);
		this.panes.put(btnSettings, paneSettings);
		this.panes.put(btnInfo, paneInfo);
		
		HamburgerSlideCloseTransition slideClose = new HamburgerSlideCloseTransition(hamburger);
		slideClose.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			slideClose.setRate(slideClose.getRate() * -1);
			slideClose.play();
			
			if(btnAnimationState) {
				for(Timeline timeline : btnAnimationsR) {
					timeline.play();
				}
			}else {
				for(Timeline timeline : btnAnimations) {
					timeline.play();
				}
			}
			
			btnAnimationState = !btnAnimationState;
		});
		
		updateTabs(currentMenu);
		updateColorScheme(currentMenu);
		updateInfo(null);
		updateOptions(null);
		updateActions(null);
		updateCommands(null);
	}
	
	public void buildColorPicker(JFXColorPicker colorPicker) {
		colorPicker.valueProperty().addListener(e -> {
			updateColors();
			updateColorScheme(currentMenu);
		});
	}
	
	public void buildHamburgerAnimations(JFXButton button, int offset) {
		Timeline btnAnimation = new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(button.translateXProperty(), 0, Interpolator.EASE_BOTH)));
		btnAnimation.setDelay(Duration.millis(offset));
		Timeline btnAnimationR = new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(button.translateXProperty(), -offset, Interpolator.EASE_BOTH)));
		btnAnimationR.setDelay(Duration.millis(offset));
		
		button.setTranslateX(-offset);
		
		btnAnimations.add(btnAnimation);
		btnAnimationsR.add(btnAnimationR);
		btnHamburgers.add(button);
		
		button.setRipplerFill(Color.valueOf(DiscordForge.getInstance().getConfig().getProperty("secondaryColor")));
		
		button.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			this.updateTabs(button.getId());
			this.updateColorScheme(button.getId());
			this.currentMenu = button.getId();
		});
	}
	
	public void updateTabs(String id) {
		for(JFXButton button : btnHamburgers) {
			if(id.equals(button.getId())) {
				this.lblTitle.setText(button.getText());
				
				for(Pane pane : panes.values()) {
					pane.setVisible(false);
				}
				
				if(panes.containsKey(button)) {
					panes.get(button).setVisible(true);
				}
			}
		}
	}
	
	public void updateColorScheme(String id) {
		for(JFXButton button : btnHamburgers) {
			if(id.equals(button.getId())) {
				button.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
			}else {
				button.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
			}
		}
		
		btnPresence.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		
		listPlugins.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		listPlugins1.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		listCommands.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		listPlugins.refresh();
		listPlugins1.refresh();
		listCommands.refresh();
		
		for(Pane pane : this.panes.values()) {
			pane.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		}
		
		btnReload.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		
		paneHamburger.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("tertiaryColor") + ";");
		paneTitle.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		paneScrollConfig.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		po.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		paneOptions.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		paneScrollAction.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		pa.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		paneActions.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		
		hamburger.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("tertiaryColor") + ";");
		
		updateActions(listPlugins.getSelectionModel().getSelectedItem());
		updateOptions(listPlugins1.getSelectionModel().getSelectedItem());
	}
	
	public void updateColors() {
		DiscordForge.getInstance().getConfig().setProperty("primaryColor", toRGBCode(colorPrimary.getValue()));
		DiscordForge.getInstance().getConfig().setProperty("secondaryColor", toRGBCode(colorSecondary.getValue()));
		DiscordForge.getInstance().getConfig().setProperty("tertiaryColor", toRGBCode(colorTertiary.getValue()));
	}
	
	public void updateInfo(BakedPlugin plugin) {
		if(plugin == null) {
			lblInfo.setText("Name: NA\nAuthor: NA\nVersion: NA");
			toggleEnabled.setSelected(false);
		}else {
			lblInfo.setText("Name: " + plugin.getMainClass().getPluginName() + "\nAuthor: " + plugin.getMainClass().getPluginAuthor() + "\nVersion: " + plugin.getMainClass().getPluginVersion());
			toggleEnabled.setSelected(plugin.isEnabled());
		}
	}
	
	public void updateOptions(BakedPlugin plugin) {
		paneOptions.getChildren().clear();
		
		if(plugin == null) return;
		
		DiscordForge.getInstance().getConfigManager().getLayout(plugin.getMainClass()).display(paneOptions);
	}
	
	public void updateCommands(ICommandHandler command) {
		if(command == null) {
			lblCommandInfo.setText("Name: NA\nDescription: NA\nSyntax: NA\nAliases: NA");
			toggleCommandEnabled.setSelected(false);
		}else {
			lblCommandInfo.setText(String.format("Name: %s\nDescription: %s\nSyntax: %s\nAliases: %s", command.getCommand(), command.getDescription(), command.getSyntax(), StringUtil.listToString(command.getAliases(), ", ")));
			toggleCommandEnabled.setSelected(command.isEnabled());
		}
	}
	
	public void updateActions(BakedPlugin plugin) {
		paneActions.getChildren().clear();
		
		if(plugin == null) return;
		
		DiscordForge.getInstance().getActionManager().getLayout(plugin.getMainClass()).display(paneActions);
	}
	
	public static MainController getInstance() {
		return instance;
	}
	
	public static String toRGBCode( Color color )
    {
        return String.format( "%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
	
}
