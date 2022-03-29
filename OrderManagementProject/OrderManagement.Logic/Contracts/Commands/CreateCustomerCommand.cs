using AutoMapper;
using OrderManagement.Dal;
using OrderManagement.Dtos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Handlers.Commands
{
    public record CreateCustomerCommand(CustomerForCreationDto Customer) : ICommand<CustomerDto>
    {
        


    }
}
