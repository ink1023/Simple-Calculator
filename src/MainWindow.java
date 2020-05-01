import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainWindow extends JFrame implements MouseListener{
    private JLabel expressionTextLabel;//计算器的表达式显示区域
    private JButton[] numberButtons = new JButton[12],
                      operationButtons = new JButton[5];//数字按钮和操作按钮
    private CalculatorState calState;//计算器的状态
    public MainWindow(){
        this.setTitle("Rainbow Calculator");
        this.setSize(510,410);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.placeInfoField();
        this.placeOperationField();
        this.addListeners();
        this.calState = CalculatorState.JustAfterLauch;
        this.setVisible(true);

    }

    public void addListeners(){
        for(JButton button:this.numberButtons){
            button.addMouseListener(this);
        }
        for(JButton button:this.operationButtons){
            button.addMouseListener(this);
        }
    }

    //部署计算器字符显示区域
    public void placeInfoField(){
        JPanel infoField = new JPanel();
        infoField.setLayout(new FlowLayout());
        infoField.setBackground(Color.LIGHT_GRAY);
        infoField.setPreferredSize(new Dimension(500, 100));
        this.getContentPane().add(infoField, "North");
        this.expressionTextLabel = new JLabel("0");
        this.expressionTextLabel.setPreferredSize(new Dimension(450, 80));
        Font infoFont = new Font("Comic Sans MS", Font.ITALIC, 30);
        expressionTextLabel.setFont(infoFont);
        infoField.add(this.expressionTextLabel, "Center");

    }

    //部署计算器操作区域
    public void placeOperationField(){
        Font btnFont = new Font("Comic Sans MS", Font.PLAIN, 28);
        Color btnColor = new Color(0xaa, 0xaa, 0xaa);
        Color panelBgColor = new Color(0xcc,0xcc,0xcc);
        JPanel operationField = new JPanel();
        operationField.setBackground(panelBgColor);
        operationField.setLayout(new FlowLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout());
        leftPanel.setPreferredSize(new Dimension(340,265));
        FlowLayout rightPanelLayout = new FlowLayout();
        rightPanelLayout.setVgap(7);
        rightPanel.setLayout(rightPanelLayout);
        rightPanel.setPreferredSize(new Dimension(140,265));
        leftPanel.setBackground(panelBgColor);
        rightPanel.setBackground(panelBgColor);
        operationField.add(leftPanel);
        operationField.add(rightPanel);

        this.getContentPane().add(operationField, "Center");
        for(int i=0; i<12; i++){
            this.numberButtons[i] = new JButton(String.valueOf((i+1)%10));
        }
        this.numberButtons[9].setText("C");
        this.numberButtons[10].setText("0");
        this.numberButtons[11].setText("<");
        String[] opbtnText = "+,-,×,÷,=".split(",");
        for(int i=0; i<this.operationButtons.length; i++){
            this.operationButtons[i] = new JButton(opbtnText[i]);
        }
        for(JButton button:this.numberButtons){
            button.setPreferredSize(new Dimension(100,60));
            button.setBackground(btnColor);
            button.setFont(btnFont);
            leftPanel.add(button);
        }
        for(JButton button:this.operationButtons){
            button.setPreferredSize(new Dimension(120,45));
            button.setBackground(btnColor);
            button.setFont(btnFont);
            rightPanel.add(button);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        String text = ((JButton)(e.getSource())).getText();
        switch (text){
            case "=":  // 计算操作
                try{
                    float result = ExpressionParser.parse(expressionTextLabel.getText());
                    expressionTextLabel.setText(String.valueOf(result));
                    this.calState = CalculatorState.CalculateDone;
                } catch (Exception err){
                    this.calState = CalculatorState.Error;
                    expressionTextLabel.setText("Error");
                    //System.out.println("表达式解析出错");
                }

                break;
            case "C":  // 显示区归零
                expressionTextLabel.setText("0");
                this.calState = CalculatorState.JustAfterLauch;
                break;
            case "<":  // 删除显示区的一个字符
                StringBuilder sb = new StringBuilder(this.expressionTextLabel.getText());
                if(sb.length()>=2){
                    expressionTextLabel.setText(sb.substring(0,sb.length()-1).toString());
                } else {
                    expressionTextLabel.setText("0");
                }
                break;
            default:  // 向显示区添加一个数字
                if(this.calState!=CalculatorState.Inputing ){
                    this.expressionTextLabel.setText("");
                    this.calState = CalculatorState.Inputing;
                }
                String newContent = this.expressionTextLabel.getText() + text;
                this.expressionTextLabel.setText(newContent);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

enum CalculatorState{
    JustAfterLauch,
    Inputing,
    CalculateDone,
    Error,
}