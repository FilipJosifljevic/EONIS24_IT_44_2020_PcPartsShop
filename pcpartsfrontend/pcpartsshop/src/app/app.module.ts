import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { PanelModule } from "primeng/panel";
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from "primeng/dropdown";

import { AppRoutingModule } from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {PasswordModule} from "primeng/password";
import {ButtonModule} from "primeng/button";
import { RegisterComponent } from './components/register/register.component';
import {DividerModule} from "primeng/divider";
import {FloatLabelModule} from "primeng/floatlabel";
import { ProductspageComponent } from './components/productspage/productspage.component';
import {RadioButtonModule} from "primeng/radiobutton";
import {DockModule} from "primeng/dock";
import {CardModule} from "primeng/card";
import { ProductdetailsComponent } from './components/productdetails/productdetails.component';
import {PaginatorModule} from "primeng/paginator";
import {ToolbarModule} from "primeng/toolbar";
import {InputGroupModule} from "primeng/inputgroup";
import {InputGroupAddonModule} from "primeng/inputgroupaddon";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import { AdmindashboardComponent } from './components/admindashboard/admindashboard.component';
import {MenubarModule} from "primeng/menubar";
import {BadgeModule} from "primeng/badge";
import {AvatarModule} from "primeng/avatar";
import {TagModule} from "primeng/tag";
import {MenuModule} from "primeng/menu";
import {SliderModule} from "primeng/slider";
import { UserprofileComponent } from './components/userprofile/userprofile.component';
import { CompareproductsComponent } from './components/compareproducts/compareproducts.component';
import {AutoCompleteModule} from "primeng/autocomplete";
import { CartComponent } from './components/cartcomponent/cart.component';
import {DataViewModule} from "primeng/dataview";
import {TableModule} from "primeng/table";
import {NgxStripeModule} from "ngx-stripe";
import { CardComponent } from './components/card/card.component';
import {TabViewModule} from "primeng/tabview";
import {SplitterModule} from "primeng/splitter";
import { AddProductDialogComponent } from './components/addproductdialog/addproductdialog.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatFormFieldModule} from "@angular/material/form-field";
import {DialogModule} from "primeng/dialog";
import {AuthInterceptor} from "./services/AuthInterceptor";
import {MatPaginator} from "@angular/material/paginator";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProductspageComponent,
    ProductdetailsComponent,
    AdmindashboardComponent,
    UserprofileComponent,
    CompareproductsComponent,
    CartComponent,
    CardComponent,
    AddProductDialogComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        FormsModule,
        NgxStripeModule.forRoot('pk_test_51PKmDhBY4FcDsN1R2mQT6i8J4kuuEE8QQaZwIBP1LEEvetc4EPaqoGMZ8kAnR4ZBmXpX8M1abqALfsmpw1s8x94X00jWrGwATI'),
        PanelModule,
        InputTextModule,
        PasswordModule,
        ButtonModule,
        DropdownModule,
        DividerModule,
        FloatLabelModule,
        RadioButtonModule,
        DockModule,
        CardModule,
        PaginatorModule,
        ToolbarModule,
        InputGroupModule,
        InputGroupAddonModule,
        IconFieldModule,
        InputIconModule,
        MenubarModule,
        BadgeModule,
        AvatarModule,
        TagModule,
        MenuModule,
        SliderModule,
        AutoCompleteModule,
        DataViewModule,
        TableModule,
        TabViewModule,
        SplitterModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        DialogModule,
        MatPaginator
    ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
