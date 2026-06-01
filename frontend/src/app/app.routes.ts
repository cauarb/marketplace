import { Routes } from '@angular/router';
import { ProdutoListaComponent } from './models/pages/produtos/produto-lista.component';
import { ProdutoFormComponent } from './models/pages/produtos/produto-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'produtos', pathMatch: 'full' },
  { path: 'produtos', component: ProdutoListaComponent },
  { path: 'produtos/novo', component: ProdutoFormComponent },
  { path: 'produtos/editar/:id', component: ProdutoFormComponent }
];