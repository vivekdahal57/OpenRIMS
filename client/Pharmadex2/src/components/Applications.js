import React , {Component} from 'react'
import {Container, Row, Col, Navbar, NavbarBrand, Collapse, NavbarToggler,Nav, NavItem, NavLink} from 'reactstrap'
import Navigator from './utils/Navigator'
import ApplicationSelect from './ApplicationSelect'
import ApplicationStart from './ApplicationStart'
import ActivityManager from './ActivityManager'
import Locales from './utils/Locales'
import Amendments from './Amendments'
import Deregistration from './Deregistration'
import AmendmentStart from './amendments/AmendmentStart'
import RenewalMock from './mock/RenewalMock'
import DeregMock from './mock/DeregMock copy'

/**
 * Tab Application. Switch between ApplicationSelect and ApplicationProcess components
 */
class Applications extends Component{
    constructor(props){
        super(props)
        this.state={
            menu:'',
            labels:{
                global_exit:'',
                manageapplications:'',
                newapplications:'',
                amendments:'',
                deregistration:'',
                renew:'',
            }
        }
        this.component=this.component.bind(this)
    }

    componentDidMount(){
        Locales.resolveLabels(this)
    }


    component(){
        let parStr=""
        let data={}
        switch(this.state.menu){
            case "applicationstart":
                parStr = Navigator.parameterValue();
                data = JSON.parse(parStr)
                return <ApplicationStart data={data}/>
            case "activitymanager":
                parStr = Navigator.parameterValue();
                data = JSON.parse(parStr)
                return <ActivityManager historyId={data.historyId}/>
            case "amendments":
                return <Amendments />
            case "amendmentstart":
                parStr = Navigator.parameterValue()
                data = JSON.parse(parStr)
                return <AmendmentStart data={data}/>
            case "deregistration":
                return <DeregMock />
            case "renew":
                return <RenewalMock />
            default:
                return <ApplicationSelect />
        }
    }

    render(){
        if(this.state.labels.locale == undefined){
            return []
        }
        this.state.menu=Navigator.componentName().toLowerCase()
        return(
            <Container fluid>
                <Row>
                    <Col>
                        <Navbar light expand="md">
                            <NavbarBrand>{this.state.labels.manageapplications}</NavbarBrand>
                            <NavbarToggler onClick={()=>{this.state.isOpen=!this.state.isOpen; this.setState(this.state)}} className="me-2" />
                                <Collapse isOpen={this.state.isOpen} navbar>
                                    <Nav className="me-auto" navbar>
                                        <Nav>     
                                            <NavItem active={this.state.menu=='applicationselect' 
                                            || this.state.menu=='applicationstart' 
                                            || this.state.menu=='activitymanager'
                                            || this.state.menu.length==0}>
                                                <NavLink href={"/guest"+"#"+Navigator.tabName()+"/applicationselect"}>{this.state.labels.newapplications}</NavLink>
                                            </NavItem>
                                        </Nav>
                                        <Nav>     
                                            <NavItem active={this.state.menu=='renew'}>
                                                <NavLink href={"/guest"+"#"+Navigator.tabName()+"/renew"}>{this.state.labels.renew}</NavLink>
                                            </NavItem>
                                        </Nav>
                                        <Nav>     
                                            <NavItem active={this.state.menu=='amendments'
                                                            || this.state.menu=='amendmentstart'}>
                                                <NavLink href={"/guest"+"#"+Navigator.tabName()+"/amendments"}>{this.state.labels.amendments}</NavLink>
                                            </NavItem>
                                        </Nav>
                                        <Nav>     
                                            <NavItem active={this.state.menu=='deregistration'}>
                                                <NavLink href={"/guest"+"#"+Navigator.tabName()+"/deregistration"}>{this.state.labels.deregistration}</NavLink>
                                            </NavItem>
                                        </Nav>
                                        <Nav>     
                                            <NavItem>
                                                <NavLink href={"/"+Navigator.tabSetName()+"#"}>{this.state.labels.global_exit}</NavLink>
                                            </NavItem>
                                        </Nav>
                                    </Nav>
                                </Collapse>
                        </Navbar>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        {this.component()}
                    </Col>
                </Row>
            </Container>
        )
    }


}
export default Applications
Applications.propTypes={
    
}